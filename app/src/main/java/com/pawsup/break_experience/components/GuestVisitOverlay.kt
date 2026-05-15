package com.pawsup.break_experience.components

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pawsup.R
import com.pawsup.cats.Cat
import com.pawsup.cats.CatAssetResolver
import com.pawsup.cats.CatVoiceProvider
import com.pawsup.ui.theme.CrimsonTextItalicFamily

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GuestVisitOverlay(
    guestCat: Cat,
    onMeetGuest: (Cat) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember(guestCat.id) {
        runCatching {
            context.assets.open(CatAssetResolver.poster(guestCat.id)).use {
                BitmapFactory.decodeStream(it)?.asImageBitmap()
            }
        }.getOrNull()
    }

    val adverb = CatVoiceProvider.getGuestAdverb(guestCat.id)
    val bodyText = stringResource(R.string.guest_visit_body, guestCat.displayName, adverb)

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1F1A))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = guestCat.displayName,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Fit,
                            alpha = 0.8f
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    Text(
                        text = bodyText,
                        fontFamily = CrimsonTextItalicFamily,
                        fontSize = 18.sp,
                        color = Color(0xFFF5ECD7),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(onClick = onDismiss) {
                            Text(stringResource(R.string.guest_visit_dismiss))
                        }
                        Button(onClick = { onMeetGuest(guestCat) }) {
                            Text(stringResource(R.string.guest_visit_cta, guestCat.displayName))
                        }
                    }
                }
            }
        }
    }
}
