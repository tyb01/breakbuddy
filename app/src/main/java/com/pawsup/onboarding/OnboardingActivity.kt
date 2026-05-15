package com.pawsup.onboarding

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.pawsup.R
import com.pawsup.apppicker.AppPickerActivity
import com.pawsup.cats.CatAssetResolver
import com.pawsup.break_experience.components.VideoPlayerCompositor
import com.pawsup.data.UserPreferences
import com.pawsup.monitoring.MonitoringService
import com.pawsup.settings.SettingsActivity
import com.pawsup.ui.theme.CrimsonTextFamily
import com.pawsup.ui.theme.PawsUpTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    @Inject lateinit var prefs: UserPreferences

    private var currentStep by mutableIntStateOf(0)

    private val notifPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* handled in compose via checking state */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PawsUpTheme {
                val scope = rememberCoroutineScope()
                OnboardingFlow(
                    step = currentStep,
                    onNext = { currentStep++ },
                    onOpenAppPicker = {
                        startActivity(Intent(this, AppPickerActivity::class.java))
                    },
                    onGrantUsage = {
                        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                    },
                    onGrantOverlay = {
                        startActivity(
                            Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:$packageName"))
                        )
                    },
                    onGrantNotifications = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notifPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        }
                    },
                    onFinish = {
                        scope.launch {
                            prefs.setOnboardingCompleted(true)
                        }
                        ContextCompat.startForegroundService(
                            this,
                            Intent(this, MonitoringService::class.java)
                        )
                        startActivity(Intent(this, SettingsActivity::class.java))
                        finish()
                    },
                    isUsageGranted = { hasUsagePermission() },
                    isOverlayGranted = { Settings.canDrawOverlays(this) },
                    isNotifGranted = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ContextCompat.checkSelfPermission(
                                this, android.Manifest.permission.POST_NOTIFICATIONS
                            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                        } else true
                    }
                )
            }
        }
    }

    private fun hasUsagePermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        }
        return mode == AppOpsManager.MODE_ALLOWED
    }
}

@Composable
private fun OnboardingFlow(
    step: Int,
    onNext: () -> Unit,
    onOpenAppPicker: () -> Unit,
    onGrantUsage: () -> Unit,
    onGrantOverlay: () -> Unit,
    onGrantNotifications: () -> Unit,
    onFinish: () -> Unit,
    isUsageGranted: () -> Boolean,
    isOverlayGranted: () -> Boolean,
    isNotifGranted: () -> Boolean
) {
    AnimatedContent(
        targetState = step,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                slideOutHorizontally { -it } + fadeOut()
        }
    ) { currentStep ->
        when (currentStep) {
            0 -> WelcomeStep(onNext)
            1 -> AppPickerStep(onOpenAppPicker, onNext)
            2 -> LimitsStep(onNext)
            3 -> PermissionsStep(onGrantUsage, onGrantOverlay, onGrantNotifications, onNext,
                isUsageGranted, isOverlayGranted, isNotifGranted)
            4 -> MeetMisoStep(onFinish)
            else -> MeetMisoStep(onFinish)
        }
    }
}

@Composable
private fun WelcomeStep(onNext: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val bitmap = remember {
            runCatching {
                context.assets.open(CatAssetResolver.poster("miso")).use {
                    BitmapFactory.decodeStream(it)?.asImageBitmap()
                }
            }.getOrNull()
        }
        if (bitmap != null) {
            Image(
                bitmap = bitmap, contentDescription = null,
                modifier = Modifier.size(240.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.onboarding_welcome_title),
            fontFamily = CrimsonTextFamily,
            fontSize = 32.sp,
            color = Color(0xFFF5ECD7),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.onboarding_welcome_body),
            fontSize = 18.sp,
            color = Color(0xFFF5ECD7).copy(0.75f),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.onboarding_cta_start), fontSize = 18.sp)
        }
    }
}

@Composable
private fun AppPickerStep(onOpenPicker: () -> Unit, onNext: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.onboarding_apps_subtitle),
            fontSize = 20.sp,
            color = Color(0xFFF5ECD7),
            textAlign = TextAlign.Center,
            fontFamily = CrimsonTextFamily
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onOpenPicker, modifier = Modifier.fillMaxWidth()) {
            Text("Choose apps to watch", fontSize = 16.sp)
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.onboarding_apps_cta), fontSize = 16.sp)
        }
    }
}

@Composable
private fun LimitsStep(onNext: () -> Unit) {
    var visitMin by remember { mutableIntStateOf(15) }
    var breakMin by remember { mutableIntStateOf(5) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))
        Text(
            "Set your limits",
            fontFamily = CrimsonTextFamily,
            fontSize = 28.sp,
            color = Color(0xFFF5ECD7)
        )
        Spacer(Modifier.height(32.dp))

        Text(
            stringResource(R.string.onboarding_visit_label, visitMin),
            color = Color(0xFFF5ECD7),
            fontSize = 16.sp
        )
        Slider(
            value = visitMin.toFloat(),
            onValueChange = { visitMin = it.toInt() },
            valueRange = 1f..30f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Text(
            stringResource(R.string.onboarding_break_label, breakMin),
            color = Color(0xFFF5ECD7),
            fontSize = 16.sp
        )
        Slider(
            value = breakMin.toFloat(),
            onValueChange = { breakMin = it.toInt() },
            valueRange = 1f..15f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(48.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.onboarding_limits_cta), fontSize = 16.sp)
        }
    }
}

@Composable
private fun PermissionsStep(
    onGrantUsage: () -> Unit,
    onGrantOverlay: () -> Unit,
    onGrantNotifications: () -> Unit,
    onNext: () -> Unit,
    isUsageGranted: () -> Boolean,
    isOverlayGranted: () -> Boolean,
    isNotifGranted: () -> Boolean
) {
    var usageOk by remember { mutableStateOf(isUsageGranted()) }
    var overlayOk by remember { mutableStateOf(isOverlayGranted()) }
    var notifOk by remember { mutableStateOf(isNotifGranted()) }
    val allGranted = usageOk && overlayOk && notifOk

    // Re-check every time the activity comes back to the foreground (user returns
    // from system settings after granting a permission). Fires instantly on resume.
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                usageOk = isUsageGranted()
                overlayOk = isOverlayGranted()
                notifOk = isNotifGranted()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))
        Text("Almost there", fontFamily = CrimsonTextFamily, fontSize = 28.sp, color = Color(0xFFF5ECD7))
        Spacer(Modifier.height(8.dp))
        Text("We need a few permissions to work.", color = Color(0xFFF5ECD7).copy(0.7f))
        Spacer(Modifier.height(32.dp))

        PermissionRow(
            title = stringResource(R.string.onboarding_permission_usage_title),
            body = stringResource(R.string.onboarding_permission_usage_body),
            granted = usageOk,
            onGrant = { onGrantUsage(); usageOk = isUsageGranted() }
        )
        Spacer(Modifier.height(16.dp))
        PermissionRow(
            title = stringResource(R.string.onboarding_permission_overlay_title),
            body = stringResource(R.string.onboarding_permission_overlay_body),
            granted = overlayOk,
            onGrant = { onGrantOverlay(); overlayOk = isOverlayGranted() }
        )
        Spacer(Modifier.height(16.dp))
        PermissionRow(
            title = stringResource(R.string.onboarding_permission_notification_title),
            body = stringResource(R.string.onboarding_permission_notification_body),
            granted = notifOk,
            onGrant = { onGrantNotifications(); notifOk = isNotifGranted() }
        )

        Spacer(Modifier.height(48.dp))
        Button(
            onClick = onNext,
            enabled = allGranted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.onboarding_all_done_cta), fontSize = 16.sp)
        }
    }
}

@Composable
private fun PermissionRow(
    title: String,
    body: String,
    granted: Boolean,
    onGrant: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1F1A)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color(0xFFF5ECD7), fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))
                Text(body, color = Color(0xFFF5ECD7).copy(0.65f), fontSize = 13.sp)
            }
            Spacer(Modifier.width(12.dp))
            if (granted) {
                Text("✓", color = Color(0xFF4CAF50), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            } else {
                OutlinedButton(onClick = onGrant) {
                    Text(stringResource(R.string.onboarding_permission_grant))
                }
            }
        }
    }
}

@Composable
private fun MeetMisoStep(onFinish: () -> Unit) {
    var showFarewell by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08)),
        contentAlignment = Alignment.Center
    ) {
        VideoPlayerCompositor(
            assetPath = CatAssetResolver.entranceVideo("miso"),
            loop = false,
            onVideoEnd = {
                showFarewell = true
                scope.launch {
                    kotlinx.coroutines.delay(2_000)
                    onFinish()
                }
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
                .padding(horizontal = 32.dp)
        ) {
            AnimatedVisibility(visible = !showFarewell, enter = fadeIn()) {
                Text(
                    text = stringResource(R.string.onboarding_miso_intro),
                    fontFamily = CrimsonTextFamily,
                    fontSize = 26.sp,
                    color = Color(0xFFF5ECD7),
                    textAlign = TextAlign.Center
                )
            }
            AnimatedVisibility(visible = showFarewell, enter = fadeIn()) {
                Text(
                    text = stringResource(R.string.onboarding_miso_farewell),
                    fontFamily = CrimsonTextFamily,
                    fontSize = 26.sp,
                    color = Color(0xFFF5ECD7),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
