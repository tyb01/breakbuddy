package com.pawsup.break_experience.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayerCompositor(
    assetPath: String,
    loop: Boolean,
    modifier: Modifier = Modifier,
    onVideoEnd: (() -> Unit)? = null,
    playerRef: ((ExoPlayer) -> Unit)? = null
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val exoPlayer = rememberExoPlayer(context, assetPath, loop)

    LaunchedEffect(assetPath) {
        exoPlayer.seekTo(0)
        exoPlayer.play()
    }

    DisposableEffect(assetPath) {
        if (onVideoEnd != null) {
            val listener = object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) onVideoEnd()
                }
            }
            exoPlayer.addListener(listener)
            onDispose { exoPlayer.removeListener(listener) }
        } else {
            onDispose {}
        }
    }

    DisposableEffect(Unit) {
        playerRef?.invoke(exoPlayer)
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun rememberExoPlayer(
    context: Context,
    assetPath: String,
    loop: Boolean
): ExoPlayer = remember(assetPath) {
    ExoPlayer.Builder(context).build().apply {
        val uri = Uri.parse("asset:///$assetPath")
        setMediaItem(MediaItem.fromUri(uri))
        repeatMode = if (loop) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
        playWhenReady = true
        prepare()
    }
}
