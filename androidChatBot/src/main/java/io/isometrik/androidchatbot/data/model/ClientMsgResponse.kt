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
    val imageURL: String? = null,
    val productId: String?= null,
    val title: String?= null,
    val actionText: String?= null,
    val description: String?= null,
    val subtitle: String?= null,
    val buttontext: String?= null,
    val link: String?= null,
    val actionHandler: Any?= null,
    val price: String? = null,
    val table_reservations: Boolean? = null,
    val supported_order_types: Int?= null,
    val average_cost: Long?= null,
    val avg_rating: String?= null,
    val store_id: String?= null,
    val currency_code: String?= null,
    val discount_price: String?= null,
)