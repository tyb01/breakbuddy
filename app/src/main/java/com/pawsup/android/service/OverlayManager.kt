package com.pawsup.android.service

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.pawsup.android.ui.overlay.BreakCharacterAssets
import com.pawsup.android.ui.overlay.CatOverlayContent
import com.pawsup.android.ui.overlay.MochiCharacterAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OverlayManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val mainHandler = Handler(Looper.getMainLooper())

    private var windowManager: WindowManager? = null
    private var overlayView: ComposeView? = null
    private var layoutParams: WindowManager.LayoutParams? = null
    private var overlayLifecycle: OverlayLifecycleOwner? = null

    private var audioFocusRequest: AudioFocusRequest? = null
    private val audioFocusListener = AudioManager.OnAudioFocusChangeListener { }

    /**
     * WindowManager and Compose must run on the main thread. The monitor service uses a background
     * coroutine; without this marshalling, [ showOverlay ] crashes the app when the break starts.
     */
    private fun runOnMain(block: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            block()
        } else {
            mainHandler.post(block)
        }
    }

    fun showOverlay(
        breakDurationSeconds: Int,
        catName: String,
        character: BreakCharacterAssets = MochiCharacterAssets,
        onBreakEnd: () -> Unit,
    ) {
        runOnMain {
            dismissOverlaySync()
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            // Focusable + touchable overlay: underlying app (e.g. reels) loses window/audio focus and pauses.
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                PixelFormat.OPAQUE,
            )
            layoutParams = params
            val lifecycleOwner = OverlayLifecycleOwner()
            overlayLifecycle = lifecycleOwner
            lifecycleOwner.onCreate()
            lifecycleOwner.onStart()
            lifecycleOwner.onResume()

            val composeView = ComposeView(context).apply {
                setBackgroundColor(Color.BLACK)
                isFocusable = true
                isFocusableInTouchMode = true
                setViewTreeLifecycleOwner(lifecycleOwner)
                setViewTreeViewModelStoreOwner(lifecycleOwner)
                setViewTreeSavedStateRegistryOwner(lifecycleOwner)
                setContent {
                    CatOverlayContent(
                        breakDurationSeconds = breakDurationSeconds,
                        catName = catName,
                        character = character,
                        onBreakEnd = {
                            dismissOverlay()
                            onBreakEnd()
                        },
                    )
                }
            }
            overlayView = composeView
            requestBreakAudioFocus()
            windowManager?.addView(composeView, params)
            composeView.post { composeView.requestFocus() }
        }
    }

    private fun requestBreakAudioFocus() {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val req = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                        .build(),
                )
                .setOnAudioFocusChangeListener(audioFocusListener)
                .build()
            audioFocusRequest = req
            am.requestAudioFocus(req)
        } else {
            @Suppress("DEPRECATION")
            am.requestAudioFocus(
                audioFocusListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT,
            )
        }
    }

    private fun abandonBreakAudioFocus() {
        val am = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let { am.abandonAudioFocusRequest(it) }
        } else {
            @Suppress("DEPRECATION")
            am.abandonAudioFocus(audioFocusListener)
        }
        audioFocusRequest = null
    }

    fun dismissOverlay() {
        runOnMain { dismissOverlaySync() }
    }

    /** Must run on the main thread (caller [runOnMain] or [showOverlay] internal path). */
    private fun dismissOverlaySync() {
        abandonBreakAudioFocus()
        overlayLifecycle?.onDestroy()
        overlayLifecycle = null
        val view = overlayView ?: return
        runCatching {
            windowManager?.removeViewImmediate(view)
        }
        overlayView = null
        windowManager = null
        layoutParams = null
    }

    fun isShowing(): Boolean = overlayView != null
}
