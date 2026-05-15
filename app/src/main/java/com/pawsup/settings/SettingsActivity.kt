package com.pawsup.settings

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
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
import com.pawsup.cats.CatAssetResolver
import com.pawsup.cats.CatRegistry
import com.pawsup.cats.CatTier
import com.pawsup.paywall.PaywallActivity
import com.pawsup.ui.theme.CrimsonTextFamily
import com.pawsup.ui.theme.PawsUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    private val vm: SettingsViewModel by viewModels()
    @Inject lateinit var catRegistry: CatRegistry

    override fun onResume() {
        super.onResume()
        vm.refreshPermissions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PawsUpTheme {
                val state by vm.state.collectAsStateWithLifecycle()
                var showCatPicker by remember { mutableStateOf(false) }
                var showReliability by remember { mutableStateOf(false) }
                var editVisitLimit by remember { mutableStateOf(false) }
                var editBreakTime by remember { mutableStateOf(false) }

                if (showCatPicker) {
                    CatPickerDialog(
                        cats = catRegistry.all,
                        activeCatId = state.activeCat?.id ?: "miso",
                        entitlements = state.entitlements,
                        onSelect = { vm.setActiveCat(it); showCatPicker = false },
                        onAdopt = { catId ->
                            showCatPicker = false
                            startActivity(Intent(this, PaywallActivity::class.java)
                                .putExtra(PaywallActivity.EXTRA_CAT_ID, catId))
                        },
                        onDismiss = { showCatPicker = false }
                    )
                } else if (showReliability) {
                    ReliabilityPanel(state = state, onDismiss = { showReliability = false })
                } else {
                    SettingsScreen(
                        state = state,
                        onCompanionTap = { showCatPicker = true },
                        onEditApps = { startActivity(Intent(this, AppPickerActivity::class.java)) },
                        onEditVisitLimit = { editVisitLimit = true },
                        onEditBreakTime = { editBreakTime = true },
                        onCafe = { showCatPicker = true },
                        onReliability = { showReliability = true },
                        onMonitorMeToggle = { vm.setMonitorMe(it, this) }
                    )
                }

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

@Composable
private fun SettingsScreen(
    state: SettingsUiState,
    onCompanionTap: () -> Unit,
    onEditApps: () -> Unit,
    onEditVisitLimit: () -> Unit,
    onEditBreakTime: () -> Unit,
    onCafe: () -> Unit,
    onReliability: () -> Unit,
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
        Spacer(Modifier.height(32.dp))

        // Your companion today
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

        // Visit limit
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

        SettingsRow(
            label = stringResource(R.string.settings_the_cafe),
            value = "",
            onClick = onCafe
        )
        SettingsRow(
            label = stringResource(R.string.settings_reliability),
            value = "",
            onClick = onReliability
        )

        Spacer(Modifier.height(8.dp))
        HorizontalDivider(color = Color.White.copy(0.1f))
        Spacer(Modifier.height(8.dp))

        // Monitor Me toggle
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

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun ActiveCatCard(cat: Cat, onClick: () -> Unit, context: android.content.Context) {
    val bitmap = remember(cat.id) {
        runCatching {
            context.assets.open(CatAssetResolver.poster(cat.id)).use {
                BitmapFactory.decodeStream(it)?.asImageBitmap()
            }
        }.getOrNull()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1F1A)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap, contentDescription = cat.displayName,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(16.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(cat.displayName, color = Color(0xFFF5ECD7), fontSize = 20.sp,
                    fontFamily = CrimsonTextFamily, fontWeight = FontWeight.Bold)
                Text(cat.personalityBlurb, color = Color(0xFFF5ECD7).copy(0.6f),
                    fontSize = 13.sp, fontStyle = FontStyle.Italic)
            }
            Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFFF5ECD7).copy(0.4f))
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
private fun CatPickerDialog(
    cats: List<Cat>,
    activeCatId: String,
    entitlements: com.pawsup.billing.Entitlements,
    onSelect: (String) -> Unit,
    onAdopt: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.cat_picker_title), fontFamily = CrimsonTextFamily, fontSize = 24.sp) },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cats) { cat ->
                    val owned = entitlements.owns(cat.id)
                    CatGridCard(
                        cat = cat,
                        isActive = cat.id == activeCatId,
                        isOwned = owned,
                        onClick = {
                            if (owned) onSelect(cat.id)
                            else onAdopt(cat.id)
                        },
                        context = context
                    )
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } },
        containerColor = Color(0xFF1A1208)
    )
}

@Composable
private fun CatGridCard(
    cat: Cat,
    isActive: Boolean,
    isOwned: Boolean,
    onClick: () -> Unit,
    context: android.content.Context
) {
    val bitmap = remember(cat.id) {
        runCatching {
            context.assets.open(CatAssetResolver.poster(cat.id)).use {
                BitmapFactory.decodeStream(it)?.asImageBitmap()
            }
        }.getOrNull()
    }
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
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap, contentDescription = cat.displayName,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        contentScale = ContentScale.Crop
                    )
                }
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
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReliabilityPanel(state: SettingsUiState, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(32.dp))
        Text(
            stringResource(R.string.settings_reliability),
            fontFamily = CrimsonTextFamily,
            fontSize = 24.sp,
            color = Color(0xFFF5ECD7)
        )
        Spacer(Modifier.height(24.dp))

        StatusRow("Usage access", state.usagePermGranted)
        StatusRow("Overlay permission", state.overlayPermGranted)
        StatusRow("Notification permission", state.notifPermGranted)

        Spacer(Modifier.height(48.dp))
        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
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
                Text("$value min", textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
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
