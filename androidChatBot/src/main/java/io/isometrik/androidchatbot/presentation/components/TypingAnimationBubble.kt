package io.isometrik.androidchatbot.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TypingAnimationBubble() {
    val dotCount = 3
    var currentVisibleDots by remember { mutableStateOf(0) }

    // InfiniteTransition allows for animations that loop indefinitely
    val transition = rememberInfiniteTransition()
    
    val animation by transition.animateFloat(
        initialValue = 0f,
        targetValue = dotCount.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing), // 1 second animation duration
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(animation) {
        currentVisibleDots = (animation % (dotCount + 1)).toInt()
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        repeat(dotCount) { index ->
            val isVisible = index < currentVisibleDots

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .animateContentSize() // Smooth animation for size changes
                    .let {
                        if (isVisible) {
                            it.then(Modifier.size(10.dp))
                        } else {
                            it.then(Modifier.size(5.dp)) // Shrink dots when hidden
                        }
                    }
                    .background(
                        if (isVisible) Color.Gray else Color.Transparent
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTypingAnimationBubble() {
    MaterialTheme {
        TypingAnimationBubble()
    }
}
