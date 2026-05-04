package com.pawsup.android.ui.screens.onboarding

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pawsup.android.R
import com.pawsup.android.ui.theme.CharcoalDark
import com.pawsup.android.ui.theme.CreamBackground
import com.pawsup.android.ui.theme.OrangePrimary
import kotlinx.coroutines.delay

@Composable
fun OnboardingFlow(
    onRequestBatteryExemption: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    val step by viewModel.step.collectAsState()
    val usageOk by viewModel.usageGranted.collectAsState()
    val overlayOk by viewModel.overlayGranted.collectAsState()
    val notifOk by viewModel.notificationsGranted.collectAsState()

    val contentModifier = if (step == 0) {
        Modifier.fillMaxSize()
    } else {
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    }

    Column(
        modifier = contentModifier,
    ) {
        if (step > 0) {
            StepHeader(stepIndex = step)
            Spacer(Modifier.height(16.dp))
        }
        when (step) {
            0 -> WelcomePage(onNext = { viewModel.next() })
            1 -> UsagePermissionPage(
                granted = usageOk,
                onOpenSettings = viewModel::openUsageSettings,
                onContinue = { viewModel.next() },
            )
            2 -> OverlayPermissionPage(
                granted = overlayOk,
                onOpenSettings = viewModel::openOverlaySettings,
                onContinue = { viewModel.next() },
            )
            3 -> FinishPage(
                notifOk = notifOk,
                onOpenNotifSettings = viewModel::requestNotificationPermission,
                onStart = { viewModel.finishOnboarding(onRequestBatteryExemption) },
            )
        }
    }
}

@Composable
private fun StepHeader(stepIndex: Int) {
    val total = 4
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            "Step ${stepIndex + 1} of $total",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        LinearProgressIndicator(
            progress = { (stepIndex + 1) / total.toFloat() },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun WelcomePage(onNext: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.large)
            .background(CharcoalDark)
            .padding(horizontal = 4.dp, vertical = 6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 22.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = CreamBackground.copy(alpha = 0.15f),
                    ),
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            stringResource(R.string.onboarding_badge),
                            style = MaterialTheme.typography.labelLarge,
                            color = CreamBackground,
                        )
                    }
                }
                Text(
                    stringResource(R.string.onboarding_hook),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = CreamBackground,
                )
                Text(
                    stringResource(R.string.onboarding_welcome_body),
                    style = MaterialTheme.typography.bodyLarge,
                    color = CreamBackground.copy(alpha = 0.8f),
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_cat),
                    contentDescription = null,
                    modifier = Modifier.size(190.dp),
                )
                Spacer(Modifier.height(26.dp))
                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = CharcoalDark,
                    ),
                ) {
                    Text(stringResource(R.string.onboarding_primary_cta))
                }
            }
        }
    }
}

@Composable
private fun UsagePermissionPage(
    granted: Boolean,
    onOpenSettings: () -> Unit,
    onContinue: () -> Unit,
) {
    LaunchedEffect(granted) {
        if (granted) {
            delay(450)
            onContinue()
        }
    }
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_cat),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "So PawsUp knows when you’re inside an app",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "We need to see which apps you open so PawsUp knows when to show up.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(20.dp))
        if (granted) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(48.dp),
            )
            Text(
                "You’re all set here.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF2E7D32),
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Heading to the next step…",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            Button(
                onClick = onOpenSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Text("Open phone settings for me")
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Example: when you open Instagram or YouTube, PawsUp knows the timer should start.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun OverlayPermissionPage(
    granted: Boolean,
    onOpenSettings: () -> Unit,
    onContinue: () -> Unit,
) {
    LaunchedEffect(granted) {
        if (granted) {
            delay(450)
            onContinue()
        }
    }
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_cat),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "So PawsUp can appear for your breaks",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "We need to show PawsUp on your screen so they can host a calm break when you hit your limit.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(20.dp))
        if (granted) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(48.dp),
            )
            Text("Lovely — PawsUp can visit anytime.", color = Color(0xFF2E7D32))
            Spacer(Modifier.height(8.dp))
            Text(
                "Moving on…",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } else {
            Button(
                onClick = onOpenSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Text("Grant overlay permission.")
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Example: when your limit is reached, PawsUp can show a calm break screen on top.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun FinishPage(
    notifOk: Boolean,
    onOpenNotifSettings: () -> Unit,
    onStart: () -> Unit,
) {
    val tiramisu = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_cat),
            contentDescription = null,
            modifier = Modifier.size(88.dp),
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "You’re ready",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(10.dp))
        Text(
            "Default: 60 minutes in one visit, 5 minute break. Change anytime in the app.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(20.dp))
        if (tiramisu && !notifOk) {
            Text(
                "Allow notifications so Android can show the background status reliably.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = onOpenNotifSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            ) { Text("Open notification settings") }
            Spacer(Modifier.height(10.dp))
        }
        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        ) { Text("Start") }
    }
}
