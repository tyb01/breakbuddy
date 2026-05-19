package com.pawsup.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pawsup.R
import com.pawsup.apppicker.AppPickerActivity
import com.pawsup.cats.Cat
import com.pawsup.cats.CatRegistry
import com.pawsup.monitoring.OemAutostart
import com.pawsup.paywall.PaywallActivity
import com.pawsup.ui.CatPosterImage
import com.pawsup.ui.theme.CrimsonTextFamily
import com.pawsup.ui.theme.PawsUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private enum class SettingsTab(val label: String, val icon: ImageVector) {
    HOME("Home", Icons.Rounded.Home),
    CAFE("The Café", Icons.Rounded.Pets),
    RELIABILITY("Reliability", Icons.Rounded.Security)
}

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    private val vm: SettingsViewModel by viewModels()
    @Inject lateinit var catRegistry: CatRegistry

    override fun onResume() {
        super.onResume()
        vm.refreshPermissions()
    }

    private fun isIgnoringBatteryOptimizations(): Boolean =
        (getSystemService(POWER_SERVICE) as PowerManager)
            .isIgnoringBatteryOptimizations(packageName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Capture Activity reference before setContent so composable lambdas
        // inside AnimatedContent (which has AnimatedContentScope as receiver) can
        // access Activity methods without 'this' being shadowed.
        val act = this

        setContent {
            PawsUpTheme {
                val state by vm.state.collectAsStateWithLifecycle()
                var selectedTab by remember { mutableStateOf(SettingsTab.HOME) }
                var editVisitLimit by remember { mutableStateOf(false) }
                var editBreakTime by remember { mutableStateOf(false) }

                // Define callbacks here (setContent scope = Activity context)
                val onEditApps: () -> Unit = {
                    act.startActivity(Intent(act, AppPickerActivity::class.java))
                }
                val onAdopt: (String) -> Unit = { catId ->
                    act.startActivity(
                        Intent(act, PaywallActivity::class.java)
                            .putExtra(PaywallActivity.EXTRA_CAT_ID, catId)
                    )
                }
                val onMonitorMeToggle: (Boolean) -> Unit = { enabled ->
                    vm.setMonitorMe(enabled, act)
                    if (enabled && !act.isIgnoringBatteryOptimizations()) {
                        act.startActivity(
                            Intent(
                                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                                Uri.parse("package:${act.packageName}")
                            )
                        )
                    }
                }

                Scaffold(
                    containerColor = Color(0xFF0F0A08),
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color(0xFF1A1208),
                            tonalElevation = 0.dp
                        ) {
                            SettingsTab.entries.forEach { tab ->
                                NavigationBarItem(
                                    selected = selectedTab == tab,
                                    onClick = { selectedTab = tab },
                                    icon = { Icon(tab.icon, contentDescription = tab.label) },
                                    label = { Text(tab.label, fontSize = 11.sp) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor   = Color(0xFFE8A87C),
                                        selectedTextColor   = Color(0xFFE8A87C),
                                        unselectedIconColor = Color(0xFFF5ECD7).copy(0.4f),
                                        unselectedTextColor = Color(0xFFF5ECD7).copy(0.4f),
                                        indicatorColor      = Color(0xFFE8A87C).copy(0.15f)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    AnimatedContent(
                        targetState = selectedTab,
                        transitionSpec = {
                            fadeIn(animationSpec = androidx.compose.animation.core.tween(200)) togetherWith
                                fadeOut(animationSpec = androidx.compose.animation.core.tween(200))
                        },
                        modifier = Modifier.padding(innerPadding)
                    ) { tab ->
                        when (tab) {
                            SettingsTab.HOME -> HomeTab(
                                state = state,
                                onCompanionTap = { selectedTab = SettingsTab.CAFE },
                                onEditApps = onEditApps,
                                onEditVisitLimit = { editVisitLimit = true },
                                onEditBreakTime = { editBreakTime = true },
                                onMonitorMeToggle = onMonitorMeToggle
                            )
                            SettingsTab.CAFE -> CafeTab(
                                cats = catRegistry.all,
                                activeCatId = state.activeCat?.id ?: "miso",
                                entitlements = state.entitlements,
                                onSelect = { vm.setActiveCat(it) },
                                onAdopt = onAdopt
                            )
                            SettingsTab.RELIABILITY -> ReliabilityTab(state = state)
                        }
                    }
                }

                // Dialogs remain as overlays regardless of active tab
                if (editVisitLimit) {
                    NumberPickerDialog(
                        title = stringResource(R.string.settings_visit_limit),
                        current = state.visitLimitMin,
                        range = 1..60,
                        onConfirm = { vm.setVisitLimit(it); editVisitLimit = false },
                        onDismiss = { editVisitLimit = false }
                    )
                }
                if (editBreakTime) {
                    NumberPickerDialog(
                        title = stringResource(R.string.settings_break_time),
                        current = state.breakDurationMin,
                        range = 1..30,
                        onConfirm = { vm.setBreakDuration(it); editBreakTime = false },
                        onDismiss = { editBreakTime = false }
                    )
                }
            }
        }
    }
}

// ─── HOME TAB ────────────────────────────────────────────────────────────────

@Composable
private fun HomeTab(
    state: SettingsUiState,
    onCompanionTap: () -> Unit,
    onEditApps: () -> Unit,
    onEditVisitLimit: () -> Unit,
    onEditBreakTime: () -> Unit,
    onMonitorMeToggle: (Boolean) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.settings_companion_today),
            color = Color(0xFFF5ECD7).copy(0.5f),
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
        )
        state.activeCat?.let { cat ->
            ActiveCatCard(cat = cat, onClick = onCompanionTap, context = context)
        }

        Spacer(Modifier.height(24.dp))
        HorizontalDivider(color = Color.White.copy(0.1f))
        Spacer(Modifier.height(8.dp))

        SettingsRow(
            label = stringResource(R.string.settings_visit_limit),
            value = stringResource(R.string.settings_min_format, state.visitLimitMin),
            onClick = onEditVisitLimit
        )
        SettingsRow(
            label = stringResource(R.string.settings_break_time),
            value = stringResource(R.string.settings_min_format, state.breakDurationMin),
            onClick = onEditBreakTime
        )
        SettingsRow(
            label = stringResource(R.string.settings_monitored_apps),
            value = stringResource(R.string.settings_apps_count, state.monitoredCount),
            onClick = onEditApps
        )

        Spacer(Modifier.height(8.dp))
        HorizontalDivider(color = Color.White.copy(0.1f))
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Monitor me", color = Color(0xFFF5ECD7), fontSize = 16.sp)
                Text(
                    "Keep watching even when the app is closed",
                    color = Color(0xFFF5ECD7).copy(0.5f),
                    fontSize = 12.sp
                )
            }
            Switch(
                checked = state.monitorMeEnabled,
                onCheckedChange = onMonitorMeToggle
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

// ─── CAFÉ TAB ────────────────────────────────────────────────────────────────

@Composable
private fun CafeTab(
    cats: List<Cat>,
    activeCatId: String,
    entitlements: com.pawsup.billing.Entitlements,
    onSelect: (String) -> Unit,
    onAdopt: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.cat_picker_title),
            fontFamily = CrimsonTextFamily,
            fontSize = 28.sp,
            color = Color(0xFFF5ECD7),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(cats, key = { it.id }) { cat ->
                val owned = entitlements.owns(cat.id)
                CatGridCard(
                    cat = cat,
                    isActive = cat.id == activeCatId,
                    isOwned = owned,
                    onClick = { if (owned) onSelect(cat.id) else onAdopt(cat.id) },
                    context = context
                )
            }
        }
    }
}

// ─── RELIABILITY TAB ─────────────────────────────────────────────────────────

@Composable
private fun ReliabilityTab(state: SettingsUiState) {
    val context = LocalContext.current
    val pm = context.getSystemService(android.content.Context.POWER_SERVICE) as PowerManager
    var batteryOptimized by remember { mutableStateOf(!pm.isIgnoringBatteryOptimizations(context.packageName)) }
    val oemAvailable = OemAutostart.isAvailable(context)

    // Re-check instantly when the user returns from the battery optimization system screen
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                batteryOptimized = !pm.isIgnoringBatteryOptimizations(context.packageName)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.settings_reliability),
            fontFamily = CrimsonTextFamily,
            fontSize = 28.sp,
            color = Color(0xFFF5ECD7)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "All items must be green for reliable monitoring.",
            color = Color(0xFFF5ECD7).copy(0.6f),
            fontSize = 13.sp
        )
        Spacer(Modifier.height(24.dp))

        StatusRow("App usage access", state.usagePermGranted)
        StatusRow("Show over other apps", state.overlayPermGranted)
        StatusRow("Notifications", state.notifPermGranted)

        Spacer(Modifier.height(16.dp))
        HorizontalDivider(color = Color.White.copy(0.1f))
        Spacer(Modifier.height(16.dp))

        ReliabilityActionRow(
            label = "Battery optimization disabled",
            description = "Required so Android doesn't kill the monitoring service.",
            ok = !batteryOptimized,
            actionLabel = "Fix",
            onAction = {
                context.startActivity(
                    Intent(
                        Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                        Uri.parse("package:${context.packageName}")
                    )
                )
            }
        )

        if (oemAvailable) {
            Spacer(Modifier.height(12.dp))
            ReliabilityActionRow(
                label = "OEM autostart allowed",
                description = "Your device has extra background restrictions. " +
                    "Enable autostart for PawsUp to keep monitoring running reliably.",
                ok = false,
                actionLabel = "Open",
                onAction = {
                    OemAutostart.getIntent(context)?.let { context.startActivity(it) }
                }
            )
        }

        Spacer(Modifier.height(32.dp))
    }
}

// ─── SHARED COMPOSABLES ──────────────────────────────────────────────────────

@Composable
private fun ActiveCatCard(cat: Cat, onClick: () -> Unit, context: android.content.Context) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1F1A)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            CatPosterImage(cat = cat, modifier = Modifier.size(64.dp))
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    cat.displayName, color = Color(0xFFF5ECD7), fontSize = 20.sp,
                    fontFamily = CrimsonTextFamily, fontWeight = FontWeight.Bold
                )
                Text(
                    cat.personalityBlurb, color = Color(0xFFF5ECD7).copy(0.6f),
                    fontSize = 13.sp, fontStyle = FontStyle.Italic
                )
            }
            Icon(
                Icons.Rounded.Pets,
                contentDescription = null,
                tint = Color(0xFFF5ECD7).copy(0.4f)
            )
        }
    }
}

@Composable
private fun CatGridCard(
    cat: Cat,
    isActive: Boolean,
    isOwned: Boolean,
    onClick: () -> Unit,
    context: android.content.Context
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.65f)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) Color(0xFF3D2E26) else Color(0xFF2A1F1A)
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (isActive) BorderStroke(2.dp, Color(0xFFE8A87C)) else null
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                CatPosterImage(
                    cat = cat,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    cat.displayName,
                    color = Color(0xFFF5ECD7),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            if (isActive) {
                Surface(
                    color = Color(0xFFE8A87C),
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        stringResource(R.string.cat_picker_active_badge),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            } else if (!isOwned) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White)
                        Text(
                            stringResource(R.string.cat_picker_locked_badge),
                            color = Color.White, fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color(0xFFF5ECD7), fontSize = 16.sp, modifier = Modifier.weight(1f))
        if (value.isNotBlank()) {
            Text(value, color = Color(0xFFF5ECD7).copy(0.5f), fontSize = 16.sp)
            Spacer(Modifier.width(4.dp))
        }
        Text("›", color = Color(0xFFF5ECD7).copy(0.3f), fontSize = 20.sp)
    }
}

@Composable
private fun StatusRow(label: String, ok: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color(0xFFF5ECD7), fontSize = 16.sp)
        Text(
            if (ok) "✓" else "✗",
            color = if (ok) Color(0xFF4CAF50) else Color(0xFFE57373),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ReliabilityActionRow(
    label: String,
    description: String,
    ok: Boolean,
    actionLabel: String,
    onAction: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (ok) "✓" else "✗",
                color = if (ok) Color(0xFF4CAF50) else Color(0xFFE57373),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(28.dp)
            )
            Text(label, color = Color(0xFFF5ECD7), fontSize = 15.sp, modifier = Modifier.weight(1f))
            if (!ok) {
                Spacer(Modifier.width(8.dp))
                OutlinedButton(
                    onClick = onAction,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(actionLabel, fontSize = 13.sp)
                }
            }
        }
        if (!ok) {
            Text(
                description,
                color = Color(0xFFF5ECD7).copy(0.5f),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 28.dp, top = 2.dp, bottom = 4.dp)
            )
        }
    }
}

@Composable
private fun NumberPickerDialog(
    title: String,
    current: Int,
    range: IntRange,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var value by remember { mutableIntStateOf(current) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column {
                Text(
                    "$value min",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Slider(
                    value = value.toFloat(),
                    onValueChange = { value = it.toInt() },
                    valueRange = range.first.toFloat()..range.last.toFloat()
                )
            }
        },
        confirmButton = { Button(onClick = { onConfirm(value) }) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
