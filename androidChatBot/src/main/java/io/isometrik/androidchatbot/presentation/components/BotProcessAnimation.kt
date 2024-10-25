package io.isometrik.androidchatbot.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.presentation.extensions.toColor
import kotlinx.coroutines.delay

@Composable
fun BotProcessAnimation(modifier: Modifier = Modifier, preferences: UiPreferences) {
    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2000
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 200 with LinearOutSlowInEasing
                        0.0f at 400 with LinearOutSlowInEasing
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    val dys = dots.map { it.value }

    val travelDistance = with(LocalDensity.current) { 15.dp.toPx() }

    Row(modifier = modifier) {
        dys.forEachIndexed { index, dy ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 1.dp)
                    .size(12.dp)
                    .graphicsLayer {
                        translationY = -dy * travelDistance
                    }
                    .background(color = preferences.primary_color.toColor(), shape = CircleShape)
            ) {
                // You can put content here if needed
            }
        }
    }
}

@Preview
@Composable
fun previewProcessAnimation(){
    BotProcessAnimation(preferences = previewUiPreferences)
}
