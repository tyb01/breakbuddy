package com.pawsup.android.ui.overlay

import android.graphics.Color as AndroidColor
import android.net.Uri
import android.view.Gravity
import android.view.Surface
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.TextureView
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

private const val WALK_OUT_MS = 2000L

private const val WEBM_SLIDE_IN_MS = 3000

private enum class OverlayPhase { WalkIn, Idle, WalkOut }

private enum class WebmVideoStage { Intro, Idle }

@Composable
fun CatOverlayContent(
    breakDurationSeconds: Int,
    catName: String,
    character: BreakCharacterAssets,
    onBreakEnd: () -> Unit,
) {
    val totalMs = max(breakDurationSeconds * 1000L, WALK_OUT_MS + 500L)
    val startRt = remember { android.os.SystemClock.elapsedRealtime() }

    var phase by remember { mutableStateOf(OverlayPhase.WalkIn) }
    var walkInComplete by remember { mutableStateOf(false) }
    var walkOutComplete by remember { mutableStateOf(false) }
    var elapsed by remember { mutableLongStateOf(0L) }

    val remainingMs = max(0L, totalMs - elapsed)
    val remainingSec = ceil(remainingMs / 1000.0).toInt()
    val mm = remainingSec / 60
    val ss = remainingSec % 60
    val timeLabel = String.format("%02d:%02d", mm, ss)

    LaunchedEffect(Unit) {
        while (isActive) {
            elapsed = android.os.SystemClock.elapsedRealtime() - startRt
            val rem = max(0L, totalMs - elapsed)

            if (phase == OverlayPhase.WalkIn && walkInComplete) {
                phase = OverlayPhase.Idle
            }

            if (rem <= WALK_OUT_MS && phase != OverlayPhase.WalkOut) {
                phase = OverlayPhase.WalkOut
            }

            if (phase == OverlayPhase.WalkOut && walkOutComplete) {
                onBreakEnd()
                break
            }
            delay(48)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        WebmOverlayVideoSlot(
            introRes = character.overlayWebmIntro,
            idleRes = character.overlayWebmIdle,
            phase = phase,
            onIntroFinished = { walkInComplete = true },
            onExitAnimationFinished = { walkOutComplete = true },
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Time for a break with $catName!",
                color = Color.White,
                fontSize = 20.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "$catName is here to help you reset.",
                color = Color.White.copy(alpha = 0.88f),
                fontSize = 15.sp,
                lineHeight = 21.sp,
                modifier = Modifier.padding(top = 8.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.weight(1f, fill = true))

            Text(
                text = timeLabel,
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 28.dp),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 60.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.75f),
                        offset = Offset(0f, 2f),
                        blurRadius = 20f,
                    ),
                ),
            )

            Spacer(modifier = Modifier.weight(1.2f, fill = true))

            Text(
                text = "Hang tight — you’ll be back soon.",
                color = Color.White.copy(alpha = 0.78f),
                fontSize = 15.sp,
                lineHeight = 21.sp,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
        }
    }
}

@Composable
private fun WebmOverlayVideoSlot(
    introRes: Int,
    idleRes: Int,
    phase: OverlayPhase,
    onIntroFinished: () -> Unit,
    onExitAnimationFinished: () -> Unit,
) {
    var introTarget by remember { mutableFloatStateOf(1f) }
    LaunchedEffect(Unit) {
        introTarget = 0f
    }
    val introFrac by animateFloatAsState(
        targetValue = introTarget,
        animationSpec = tween(WEBM_SLIDE_IN_MS, easing = LinearEasing),
        label = "webm_slide_in",
    )

    var exitTarget by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(phase) {
        if (phase == OverlayPhase.WalkOut) {
            exitTarget = 1f
        }
    }
    val exitFrac by animateFloatAsState(
        targetValue = exitTarget,
        animationSpec = tween(WALK_OUT_MS.toInt(), easing = LinearEasing),
        label = "webm_slide_out",
    )

    val videoStage = remember { mutableStateOf(WebmVideoStage.Intro) }
    val stage = videoStage.value
    var exitReported by remember { mutableStateOf(false) }

    LaunchedEffect(exitFrac, phase) {
        if (phase == OverlayPhase.WalkOut && exitFrac >= 0.999f && !exitReported) {
            exitReported = true
            onExitAnimationFinished()
        }
    }

    var intrinsicPx by remember(introRes, idleRes) { mutableStateOf<Pair<Int, Int>?>(null) }

    val context = LocalContext.current
    val introUri = remember(introRes, context.packageName) {
        Uri.parse("android.resource://${context.packageName}/$introRes")
    }
    val idleUri = remember(idleRes, context.packageName) {
        Uri.parse("android.resource://${context.packageName}/$idleRes")
    }

    val onIntroDoneState = rememberUpdatedState(onIntroFinished)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val maxWpx = constraints.maxWidth.toFloat().coerceAtLeast(1f)
        val maxHpx = constraints.maxHeight.toFloat().coerceAtLeast(1f)
        val offsetPx = when (stage) {
            WebmVideoStage.Intro -> maxWpx * introFrac
            WebmVideoStage.Idle -> -maxWpx * exitFrac
        }

        fun applyVideoLayout(frame: FrameLayout) {
            val tv = frame.getChildAt(0) as? TextureView ?: return
            val pair = intrinsicPx ?: return
            val vw = pair.first.toFloat().coerceAtLeast(1f)
            val vh = pair.second.toFloat().coerceAtLeast(1f)
            val scale = min(maxWpx / vw, maxHpx / vh)
            val lw = (vw * scale).roundToInt().coerceAtLeast(1)
            val lh = (vh * scale).roundToInt().coerceAtLeast(1)
            val lp = FrameLayout.LayoutParams(lw, lh, Gravity.CENTER)
            if (tv.layoutParams?.width != lp.width || tv.layoutParams?.height != lp.height) {
                tv.layoutParams = lp
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AndroidView(
                factory = { ctx ->
                    val frame = FrameLayout(ctx).apply {
                        setBackgroundColor(AndroidColor.BLACK)
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                    }
                    val textureView = TextureView(ctx).apply {
                        isOpaque = false
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            Gravity.CENTER,
                        )
                    }
                    frame.addView(textureView)
                    val controller = WebmTextureController(
                        context = ctx,
                        textureView = textureView,
                        introUri = introUri,
                        idleUri = idleUri,
                        videoStage = videoStage,
                        onIntroDone = { onIntroDoneState.value() },
                        onVideoSize = { w, h -> if (w > 0 && h > 0) intrinsicPx = w to h },
                    )
                    textureView.surfaceTextureListener = controller
                    frame.tag = controller
                    frame
                },
                update = { frame -> applyVideoLayout(frame) },
                onRelease = { frame ->
                    (frame.tag as? WebmTextureController)?.release()
                    frame.tag = null
                },
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(offsetPx.roundToInt(), 0) },
            )
        }
    }
}

private class WebmTextureController(
    private val context: android.content.Context,
    private val textureView: TextureView,
    private val introUri: Uri,
    private val idleUri: Uri,
    private val videoStage: MutableState<WebmVideoStage>,
    private val onIntroDone: () -> Unit,
    private val onVideoSize: (Int, Int) -> Unit,
) : TextureView.SurfaceTextureListener {

    private val player = MediaPlayer()
    private var surface: Surface? = null
    private var released = false

    fun release() {
        if (released) return
        released = true
        runCatching {
            player.setSurface(null)
            player.release()
        }
        runCatching { surface?.release() }
        surface = null
    }

    private fun reportSize(mp: MediaPlayer) {
        val w = mp.videoWidth
        val h = mp.videoHeight
        if (w > 0 && h > 0) onVideoSize(w, h)
    }

    private fun startIntro() {
        if (released) return
        runCatching {
            player.reset()
            player.setSurface(surface)
            player.setDataSource(context, introUri)
            player.setOnPreparedListener { mp ->
                if (released) return@setOnPreparedListener
                reportSize(mp)
                mp.isLooping = false
                mp.start()
            }
            player.setOnCompletionListener {
                if (released) return@setOnCompletionListener
                if (!textureView.isAvailable) return@setOnCompletionListener
                if (videoStage.value != WebmVideoStage.Intro) return@setOnCompletionListener
                videoStage.value = WebmVideoStage.Idle
                onIntroDone()
                startIdle()
            }
            player.prepareAsync()
        }
    }

    private fun startIdle() {
        if (released) return
        runCatching {
            player.reset()
            player.setSurface(surface)
            player.setDataSource(context, idleUri)
            player.setOnCompletionListener(null)
            player.setOnPreparedListener { mp ->
                if (released) return@setOnPreparedListener
                reportSize(mp)
                mp.isLooping = true
                mp.start()
            }
            player.prepareAsync()
        }
    }

    override fun onSurfaceTextureAvailable(st: SurfaceTexture, width: Int, height: Int) {
        if (released) return
        runCatching { surface?.release() }
        surface = Surface(st)
        player.setSurface(surface)
        startIntro()
    }

    override fun onSurfaceTextureSizeChanged(st: SurfaceTexture, width: Int, height: Int) {}

    override fun onSurfaceTextureDestroyed(st: SurfaceTexture): Boolean {
        if (!released) {
            runCatching {
                player.setSurface(null)
                player.reset()
            }
        }
        runCatching { surface?.release() }
        surface = null
        return true
    }

    override fun onSurfaceTextureUpdated(st: SurfaceTexture) {}
}
