import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import io.isometrik.androidchatbot.R
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.presentation.extensions.toColor

@Composable
fun UserInput(
    onSendClick: (String) -> Unit,
    uiPreferences: UiPreferences,
    hint: String = "Write a message..."  // Placeholder hint text
) {
    val message = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp, top = 10.dp, start = 15.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Input TextField with outline and hint
        Box(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(20))
                .padding(14.dp)
                .imePadding()
        ) {
            if (message.value.isEmpty()) {
                // Display placeholder text when the text field is empty
                Text(
                    text = hint,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                )
            }

            BasicTextField(
                value = message.value,
                onValueChange = { message.value = it },
                textStyle = TextStyle(
                    color = if(uiPreferences.mode_theme == 1) Color.Black else Color.White,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(if(uiPreferences.mode_theme == 1) Color.Black else Color.White),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Round Send button with arrow icon
        Button(
            onClick = {
                if (message.value.isNotEmpty()) {
                    onSendClick(message.value)
                    message.value = ""  // Clear input after sending
                }
            },
            shape = CircleShape,
            modifier = Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = uiPreferences.primary_color.toColor())  // Dynamic button color
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_send),  // Replace with your arrow icon
                contentDescription = "Send",
                tint = Color.White  // Icon color
            )
        }
    }
}
