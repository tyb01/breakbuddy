package com.pawsup.break_experience

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import com.pawsup.R

object RecapToast {
    fun show(context: Context, summary: BreakSummary) {
        Handler(Looper.getMainLooper()).postDelayed({
            runCatching {
                val timeStr = "${summary.durationMinutes} minutes"
                val msg = context.getString(R.string.recap_visited, summary.catName, timeStr) +
                    "\n" + context.getString(R.string.recap_pets, summary.petCount)
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }, 6_000)
    }
}
