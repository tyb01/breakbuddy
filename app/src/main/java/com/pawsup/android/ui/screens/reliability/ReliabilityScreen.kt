package com.pawsup.android.ui.screens.reliability

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pawsup.android.R
import com.pawsup.android.ui.screens.home.HomeViewModel

@Composable
fun ReliabilityScreen(
    onBack: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.ui.collectAsState()
    if (state == null) return
    val s = state!!

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(stringResource(R.string.reliability_title), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.reliability_body), style = MaterialTheme.typography.bodyMedium)

        StatusCard(
            label = stringResource(R.string.reliability_monitoring_enabled),
            ok = s.prefs.serviceEnabled,
        )
        StatusCard(
            label = stringResource(R.string.reliability_usage_access),
            ok = s.usageAccessOk,
            actionLabel = stringResource(R.string.reliability_fix),
            onAction = { viewModel.openUsageAccessSettings() },
        )
        StatusCard(
            label = stringResource(R.string.reliability_overlay_permission),
            ok = s.overlayOk,
            actionLabel = stringResource(R.string.reliability_fix),
            onAction = { viewModel.openOverlaySettings() },
        )
        StatusCard(
            label = stringResource(R.string.reliability_battery_optimization),
            ok = s.batteryOptimizationsIgnored,
            actionLabel = stringResource(R.string.reliability_fix),
            onAction = { viewModel.requestBatteryExemption() },
        )
        if (s.oemAutoStartAvailable) {
            StatusCard(
                label = stringResource(R.string.reliability_autostart),
                ok = true,
                okLabel = stringResource(R.string.reliability_available),
                actionLabel = stringResource(R.string.reliability_fix_autostart),
                showActionWhenOk = true,
                showActionWhenNotOk = false,
                onAction = { viewModel.openOemAutoStartSettings() },
            )
        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
        ) { Text(stringResource(R.string.reliability_back)) }
    }
}

@Composable
private fun StatusCard(
    label: String,
    ok: Boolean,
    okLabel: String = stringResource(R.string.reliability_ok),
    notOkLabel: String = stringResource(R.string.reliability_needs_attention),
    actionLabel: String? = null,
    showActionWhenOk: Boolean = false,
    showActionWhenNotOk: Boolean = true,
    onAction: (() -> Unit)? = null,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(label, style = MaterialTheme.typography.bodyLarge)
                Text(
                    if (ok) okLabel else notOkLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (ok) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                )
            }
            val showAction = if (ok) showActionWhenOk else showActionWhenNotOk
            if (showAction && actionLabel != null && onAction != null) {
                Button(
                    onClick = onAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                ) { Text(actionLabel) }
            }
        }
    }
}
