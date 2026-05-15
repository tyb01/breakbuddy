package com.pawsup.share

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.core.content.FileProvider
import com.pawsup.cats.Cat
import com.pawsup.cats.CatAssetResolver
import java.io.File
import javax.inject.Inject

class BreakRecapGenerator @Inject constructor() {

    fun createShareIntent(context: Context, cat: Cat, durationMinutes: Int, petCount: Int): Intent {
        val bitmap = renderShareCard(context, cat, durationMinutes, petCount)
        val file = File(context.cacheDir, "break_recap.png")
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 90, it) }
        bitmap.recycle()

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareText = "I took a ${durationMinutes}-minute break with ${cat.displayName} today 🐾 #PawsUp"

        return Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, shareText)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    private fun renderShareCard(context: Context, cat: Cat, durationMinutes: Int, petCount: Int): Bitmap {
        val width = 1080
        val height = 1920
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Background gradient (approximated with solid color)
        val bgPaint = Paint().apply {
            color = android.graphics.Color.parseColor(cat.moodColorHex)
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Cat poster
        runCatching {
            val posterBitmap = context.assets.open(CatAssetResolver.poster(cat.id)).use {
                BitmapFactory.decodeStream(it)
            }
            val destTop = (height * 0.2f).toInt()
            val destBottom = (height * 0.7f).toInt()
            val destRect = android.graphics.Rect(0, destTop, width, destBottom)
            canvas.drawBitmap(posterBitmap, null, destRect, null)
            posterBitmap.recycle()
        }

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            typeface = Typeface.SERIF
        }

        // Headline
        textPaint.textSize = 56f
        canvas.drawText("I took a break with ${cat.displayName} today 🐾", width / 2f, height * 0.76f, textPaint)

        // Subline
        textPaint.textSize = 40f
        textPaint.alpha = 200
        canvas.drawText("$durationMinutes minutes of quiet. $petCount pets given.", width / 2f, height * 0.82f, textPaint)

        // Watermark
        textPaint.textSize = 28f
        textPaint.alpha = 150
        canvas.drawText("PawsUp — a cat café in your pocket", width / 2f, height * 0.94f, textPaint)

        return bitmap
    }
}
