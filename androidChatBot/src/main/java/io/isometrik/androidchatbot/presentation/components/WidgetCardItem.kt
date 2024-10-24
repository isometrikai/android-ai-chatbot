package io.isometrik.androidchatbot.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.isometrik.androidchatbot.R
import io.isometrik.androidchatbot.data.model.UiPreferences
import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.enums.OrderType
import io.isometrik.androidchatbot.presentation.extensions.toColor
import io.isometrik.androidchatbot.presentation.extensions.toFont
import io.isometrik.androidchatbot.presentation.extensions.toPrice

@Composable
fun WidgetCardItem(widget: Widget, modifier: Modifier = Modifier, uiPreferences: UiPreferences, onActionClick : (Widget) -> Unit) {
    val isDish =
        widget.avg_rating.isNullOrBlank() && widget.average_cost == null && widget.supported_order_types == null
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
            .width(if (isDish) 200.dp else 300.dp),
        colors = CardDefaults.cardColors(containerColor = previewUiPreferences.bot_bubble_color.toColor())
    ) {

        Column {
            Box {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(widget.imageURL)
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                        .crossfade(true) // Crossfade animation between thumbnail and final image
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )

                if(!isDish){
                    Row(modifier = Modifier.padding(10.dp).align(Alignment.BottomEnd), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        widget.supported_order_types?.let { supportedType ->
                            val orderType = OrderType.fromValue(supportedType)
                            if(orderType == OrderType.DELIVERY){
                                Image(
                                    painter = painterResource(id = R.drawable.ic_support_delivery),
                                    contentDescription = "delivery",
                                    modifier = Modifier.size(30.dp)
                                )
                            } else if(orderType == OrderType.SELF_PICKUP ){
                                Image(
                                    painter = painterResource(id = R.drawable.ic_support_self_pickup),
                                    contentDescription = "self pickup",
                                    modifier = Modifier.size(30.dp)
                                )
                            } else if(orderType == OrderType.BOTH){
                                Image(
                                    painter = painterResource(id = R.drawable.ic_support_delivery),
                                    contentDescription = "delivery",
                                    modifier = Modifier.size(30.dp)
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.ic_support_self_pickup),
                                    contentDescription = "self pickup",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        widget.table_reservations?.let {
                            if(it){
                                Image(
                                    painter = painterResource(id = R.drawable.ic_support_dining),
                                    contentDescription = "dining",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                    }
                }
            }

            Box(
                modifier = Modifier.padding(12.dp).fillMaxSize()
            ) {
                Column {
                    Text(
                        text = widget.title.orEmpty(),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.outline,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = uiPreferences.font_style.toFont()
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = widget.subtitle.orEmpty(),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.outline,
                            fontWeight = FontWeight.Light,
                            fontSize = 10.sp,
                            fontFamily = uiPreferences.font_style.toFont()
                        ),
                        maxLines = 1,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isDish) {
                            Row(
                                modifier = Modifier.padding(end = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    color = MaterialTheme.colorScheme.outline,
                                    text = widget.avg_rating?.toDouble()?.toPrice() ?: "0",
                                    fontSize = 12.sp
                                )

                                // Add space if needed between text and star
                                Image(
                                    painter = painterResource(id = R.drawable.ic_star), // Replace with your star drawable
                                    contentDescription = "Star Icon",
                                    modifier = Modifier.size(12.dp) // Adjust the size as needed
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = CircleShape
                                    )
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp),
                                text = "${widget.currency_code} ${widget.average_cost} for two",
                                fontSize = 12.sp,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.outline
                            )
                        } else {
                            Text(
                                text = "${widget.currency_code} ${widget.price}",
                                fontSize = 12.sp,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.outline
                            )

                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = " ${widget.discount_price.orEmpty()}",
                                fontSize = 12.sp,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.outline,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }

                    }

                }

                if (!isDish) {
                    OrderNowButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        widget.buttontext.orEmpty(),
                        previewUiPreferences
                    ) {
                        onActionClick(widget)
                    }
                }
            }

            if (isDish) {
                OrderNowButton(
                    modifier = Modifier.fillMaxWidth(),
                    widget.buttontext.orEmpty(),
                    previewUiPreferences
                ) {
                    onActionClick(widget)
                }
            }
        }
    }
}


@Composable
fun OrderNowButton(
    modifier: Modifier,
    buttonText: String,
    uiPreferences: UiPreferences,
    onClick: () -> Unit
) {
    val buttonBackgroundColor =
        uiPreferences.primary_color.toColor().copy(alpha = 0.1f) // 10% opacity

    Button(
        modifier = modifier.padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor) // Set background color with 10% opacity
    ) {
        Text(
            text = buttonText,
            color = previewUiPreferences.primary_color.toColor()
        )
    }
}

@Preview
@Composable
fun ResturanView() {

    WidgetCardItem(
        previewWidget,
        modifier = Modifier.fillMaxWidth().height(250.dp),
        uiPreferences = previewUiPreferences,
        onActionClick = {}
    )

}

internal val previewWidget = Widget(
    imageURL = "https://cdn.eazy-online.com/bulkimportImages/Pizza-testing-pro-16692747768532512.png",
    productId = "637f663cde580c4ca3e0d569",
    title = "Tomato Pizza",
    actionText = "American",
    description = "None",
    subtitle = "EAZY Cafe Coffee Day",
    buttontext = "Order Now",
    link = "https://apisuperapp-staging.eazy-online.com/python/product/details?parentProductId=637f663bde580c4ca3e0d567&productId=637f663cde580c4ca3e0d569&storeId=634e9cc3cc4ef4000da9d0fd",
    actionHandler = null,
    price = "₹ 150",
    table_reservations = null,
    supported_order_types = null,
    average_cost = null,
    avg_rating = null,
    store_id = "634e9cc3cc4ef4000da9d0fd",
    currency_code = "INR",
    discount_price = "₹ 150"
)

internal val previewUiPreferences = UiPreferences(
    mode_theme = 2,
    primary_color = "#2196f3",
    bot_bubble_color = "#1b1b1b",
    user_bubble_color = "#2196f3",
    font_size = "12px",
    font_style = "Arial",
    bot_bubble_font_color = "#ffffff",
    user_bubble_font_color = "#ffffff"

)
