package com.pawsup.application

import android.app.Application
import android.graphics.BitmapFactory
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.pawsup.billing.BillingClientWrapper
import com.pawsup.cats.CatAssetResolver
import com.pawsup.cats.CatRegistry
import com.pawsup.ui.CatPosterCache
import com.pawsup.ui.chromaKeyBitmap
import androidx.compose.ui.graphics.asImageBitmap
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PawsUpApplication : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory
    @Inject lateinit var billingClient: BillingClientWrapper
    @Inject lateinit var catRegistry: CatRegistry

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()
        preloadCatPosters()
    }

    private fun preloadCatPosters() {
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            catRegistry.all.forEach { cat ->
                if (CatPosterCache.get(cat.id) != null) return@forEach
                runCatching {
                    assets.open(CatAssetResolver.poster(cat.id)).use { stream ->
                        BitmapFactory.decodeStream(stream)
                    }?.let { raw ->
                        val processed = chromaKeyBitmap(raw, cat.keyColor).asImageBitmap()
                        raw.recycle()
                        CatPosterCache.put(cat.id, processed)
                    }
                }
            }
        }
    }
}
