package io.isometrik.androidchatbot.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.presentation.extensions.toColor
import io.isometrik.androidchatbot.presentation.extensions.toFont
import io.isometrik.androidchatbot.presentation.extensions.topbar

@Composable
fun TopBarWithProfile(
    uiPreferences: UiPreferences,
    profileImageUrl: String,
    username: String,
    appTitle: String,
    onBackArrowClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(uiPreferences.mode_theme.topbar() )
    ) {
        IconButton(onClick = onBackArrowClick) {
            Icon(
                imageVector =  Icons.Default.ArrowBack,
                contentDescription = "Back Arrow",
                tint = MaterialTheme.colorScheme.outline
            )

        }

        // Dynamic profile image with a placeholder color
        ProfileImageWithPlaceholder(
            modifier = Modifier.padding(vertical = 10.dp),
            profileImageUrl = profileImageUrl,
            placeholderColor = uiPreferences.user_bubble_color.toColor()
        )

        Spacer(modifier = Modifier.width(8.dp))

        // App Title and Username
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = appTitle,  // Dynamic app title
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.outline,
                    fontFamily = uiPreferences.font_style.toFont()  // Dynamic font
                )
            )
            Text(
                text = username,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                    fontFamily = uiPreferences.font_style.toFont()  // Dynamic font
                )
            )
        }

        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun ProfileImageWithPlaceholder(modifier: Modifier = Modifier, profileImageUrl: String, placeholderColor: Color) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(placeholderColor),  // Dynamic placeholder color
        contentAlignment = Alignment.Center
    ) {
        // Using Coil to load the image from a URL
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = profileImageUrl).apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
            ),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
    }
}