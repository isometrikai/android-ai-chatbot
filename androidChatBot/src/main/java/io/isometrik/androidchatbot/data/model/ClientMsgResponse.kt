package io.isometrik.androidchatbot.data.model

class ClientMsgResponse (
    val text: String,
    val image_data: List<Any?>,
    val website_source: String,
    val sources: List<Any?>,
    val widgetData: List<WidgetDaum>,
    val input_token_count: Long,
    val id: Long,
    val parsed_request_id: Long,
    val request_id: Long,
)

data class WidgetDaum(
    val widgetId: Long,
    val type: String,
    val widget: List<Widget>,
)

data class Widget(
    val imageURL: String,
    val productId: String,
    val title: String,
    val description: String,
    val subtitle: String,
    val buttontext: String,
    val link: String?,
    val actionHandler: Any?,
    val price: String,
    val table_reservations: Boolean?,
    val supported_order_types: Int?,
    val average_cost: Long?,
    val avg_rating: String?,
    val store_id: String,
    val currency_code: String,
    val discount_price: String?,
)