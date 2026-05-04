package com.pawsup.android.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pawsup.android.R
import com.pawsup.android.ui.theme.CharcoalDark
import com.pawsup.android.ui.theme.CreamBackground
import androidx.compose.ui.graphics.Color

@Composable
fun MainScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.ui.collectAsState()
    val showSaved by viewModel.showSaved.collectAsState()
    if (state == null) return
    val s = state!!
    val cat = stringResource(R.string.cat_display_name)
    var usageText by remember(s.prefs.usageLimitMinutes) {
        mutableStateOf(s.prefs.usageLimitMinutes.toString())
    }
    var breakText by remember(s.prefs.breakDurationMinutes) {
        mutableStateOf(s.prefs.breakDurationMinutes.toString())
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        val statusTitle = when {
            !s.usageAccessOk -> stringResource(R.string.main_status_need_usage)
            !s.overlayOk -> stringResource(R.string.main_status_need_overlay)
            s.prefs.serviceEnabled && !s.serviceRunning -> stringResource(R.string.main_status_asleep)
            s.serviceRunning -> stringResource(R.string.main_status_on, cat)
            else -> stringResource(R.string.main_status_off, cat)
        }
        Text(statusTitle, style = MaterialTheme.typography.titleLarge)

        if (!s.usageAccessOk) {
            PermissionCard(
                text = stringResource(R.string.main_card_usage, cat),
                buttonLabel = stringResource(R.string.main_open_settings),
                onClick = viewModel::openUsageAccessSettings,
                containerColor = MaterialTheme.colorScheme.errorContainer,
            )
        }

        if (s.usageAccessOk && !s.overlayOk) {
            PermissionCard(
                text = stringResource(R.string.main_card_overlay, cat),
                buttonLabel = stringResource(R.string.main_allow_overlay),
                onClick = viewModel::openOverlaySettings,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            )
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(stringResource(R.string.main_monitoring_switch), modifier = Modifier.weight(1f))
                Switch(
                    checked = s.prefs.serviceEnabled,
                    onCheckedChange = viewModel::setMonitoring,
                )
            }
        }

        Text(stringResource(R.string.main_section_limits), style = MaterialTheme.typography.titleMedium)
        val limitInputOrange = colorResource(R.color.input_field_orange)
        val limitFieldTextStyle = MaterialTheme.typography.bodyLarge.merge(TextStyle(color = CharcoalDark))
        val limitFieldColors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = CharcoalDark,
            unfocusedTextColor = CharcoalDark,
            disabledTextColor = CharcoalDark.copy(alpha = 0.38f),
            errorTextColor = MaterialTheme.colorScheme.error,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            cursorColor = limitInputOrange,
            errorCursorColor = MaterialTheme.colorScheme.error,
            focusedBorderColor = limitInputOrange,
            unfocusedBorderColor = limitInputOrange,
            disabledBorderColor = limitInputOrange.copy(alpha = 0.38f),
            errorBorderColor = MaterialTheme.colorScheme.error,
            focusedLabelColor = limitInputOrange,
            unfocusedLabelColor = limitInputOrange,
            disabledLabelColor = limitInputOrange.copy(alpha = 0.38f),
            errorLabelColor = MaterialTheme.colorScheme.error,
        )
        OutlinedTextField(
            value = usageText,
            onValueChange = { usageText = it.filter { c -> c.isDigit() }.take(3) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.main_usage_limit_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = limitFieldTextStyle,
            colors = limitFieldColors,
        )
        OutlinedTextField(
            value = breakText,
            onValueChange = { breakText = it.filter { c -> c.isDigit() }.take(2) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.main_break_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            textStyle = limitFieldTextStyle,
            colors = limitFieldColors,
        )

        Button(
            onClick = {
                val u = usageText.toIntOrNull()?.coerceIn(1, 480) ?: s.prefs.usageLimitMinutes
                val b = breakText.toIntOrNull()?.coerceIn(1, 60) ?: s.prefs.breakDurationMinutes
                viewModel.saveLimits(u, b)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
        ) { Text(stringResource(R.string.main_save)) }

        if (showSaved) {
            Text(
                stringResource(R.string.main_saved),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Text(stringResource(R.string.main_targets_label), style = MaterialTheme.typography.titleMedium)

        s.appRows.forEach { row ->
            AppTargetToggle(
                row = row,
                modifier = Modifier.fillMaxWidth(),
                onToggle = { on -> viewModel.setAppEnabled(row.packageName, on) },
            )
        }
    }
}

@Composable
private fun PermissionCard(
    text: String,
    buttonLabel: String,
    onClick: () -> Unit,
    containerColor: androidx.compose.ui.graphics.Color,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text, style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) { Text(buttonLabel) }
        }
    }
}

@Composable
private fun AppTargetToggle(
    row: TrackedAppRow,
    modifier: Modifier = Modifier,
    onToggle: (Boolean) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        ),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(row.iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Text(
                row.displayName,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp),
            )
            Switch(
                checked = row.enabled,
                onCheckedChange = onToggle,
            )
        }
    }
}


