package io.isometrik.androidchatbot.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun AlertDialogApp(
    title: String = "Confirm Exit",
    message: String = "Are you sure you want to go back?",
    confirmButtonText: String = "Yes",
    dismissButtonText: String = "No",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = {

                    onConfirm()
                }) {
                    Text(confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(dismissButtonText)
                }
            }
        )
}
