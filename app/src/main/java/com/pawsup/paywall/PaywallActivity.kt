package com.pawsup.paywall

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pawsup.R
import com.pawsup.cats.Cat
import com.pawsup.cats.CatAssetResolver
import com.pawsup.ui.theme.CrimsonTextFamily
import com.pawsup.ui.theme.PawsUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaywallActivity : ComponentActivity() {

    private val vm: PaywallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PawsUpTheme {
                val state by vm.state.collectAsStateWithLifecycle()

                if (state.purchaseSuccess) {
                    PurchaseSuccessScreen(
                        catName = state.cat?.displayName ?: "",
                        onDone = { finish() }
                    )
                } else {
                    PaywallScreen(
                        state = state,
                        onPeriodChange = vm::setPeriod,
                        onAdoptCat = { vm.adoptCat(this) },
                        onAdoptCafe = { vm.adoptCafe(this) },
                        onDismiss = { finish() }
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_CAT_ID = "cat_id"
    }
}

@Composable
private fun PaywallScreen(
    state: PaywallUiState,
    onPeriodChange: (PricePeriod) -> Unit,
    onAdoptCat: () -> Unit,
    onAdoptCafe: () -> Unit,
    onDismiss: () -> Unit
) {
    val cat = state.cat ?: return
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08))
            .verticalScroll(rememberScrollState())
    ) {
        // Cat poster — full-bleed top half with mood gradient
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val bitmap = remember(cat.id) {
                runCatching {
                    context.assets.open(CatAssetResolver.poster(cat.id)).use {
                        BitmapFactory.decodeStream(it)?.asImageBitmap()
                    }
                }.getOrNull()
            }
            if (bitmap != null) {
                Image(
                    bitmap = bitmap,
                    contentDescription = cat.displayName,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(android.graphics.Color.parseColor(cat.moodColorHex)).copy(alpha = 0.6f)
                            )
                        )
                    )
            )
        }

        Column(modifier = Modifier.padding(24.dp)) {
            // Cat name + blurb
            Text(
                text = cat.displayName,
                fontFamily = CrimsonTextFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5ECD7)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = cat.personalityBlurb,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color(0xFFF5ECD7).copy(alpha = 0.7f)
            )

            Spacer(Modifier.height(20.dp))

            // Monthly/Yearly toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = state.period == PricePeriod.MONTHLY,
                    onClick = { onPeriodChange(PricePeriod.MONTHLY) },
                    label = { Text(stringResource(R.string.paywall_monthly)) }
                )
                Spacer(Modifier.width(8.dp))
                FilterChip(
                    selected = state.period == PricePeriod.YEARLY,
                    onClick = { onPeriodChange(PricePeriod.YEARLY) },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(stringResource(R.string.paywall_yearly))
                            Spacer(Modifier.width(4.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.paywall_yearly_badge),
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            // Adopt just [cat] card
            PurchaseCard(
                title = stringResource(R.string.paywall_adopt_one, cat.displayName),
                price = state.catPrice,
                onClick = onAdoptCat
            )

            Spacer(Modifier.height(12.dp))

            // Adopt the whole café card
            PurchaseCard(
                title = stringResource(R.string.paywall_adopt_all),
                subtitle = stringResource(R.string.paywall_adopt_all_subtitle),
                price = state.cafePrice,
                onClick = onAdoptCafe
            )

            Spacer(Modifier.height(24.dp))

            // Dismiss
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(stringResource(R.string.paywall_dismiss), color = Color.White.copy(0.5f))
            }

            Spacer(Modifier.height(8.dp))

            // Legal footer
            Text(
                text = stringResource(R.string.paywall_legal),
                fontSize = 11.sp,
                color = Color.White.copy(0.4f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PurchaseCard(
    title: String,
    price: String,
    onClick: () -> Unit,
    subtitle: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1F1A)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color(0xFFF5ECD7), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                if (subtitle != null) {
                    Text(subtitle, color = Color(0xFFF5ECD7).copy(0.6f), fontSize = 13.sp)
                }
            }
            Text(
                text = price,
                color = Color(0xFFE8A87C),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun PurchaseSuccessScreen(catName: String, onDone: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A08)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Text(
                text = stringResource(R.string.paywall_success_title, catName),
                fontFamily = CrimsonTextFamily,
                fontSize = 28.sp,
                color = Color(0xFFF5ECD7),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.paywall_success_body),
                fontSize = 16.sp,
                color = Color(0xFFF5ECD7).copy(0.7f),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))
            Button(onClick = onDone) {
                Text("Continue")
            }
        }
    }
}
