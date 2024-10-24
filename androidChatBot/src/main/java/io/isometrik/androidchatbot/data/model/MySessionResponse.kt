package io.isometrik.androidchatbot.data.model


data class MySessionResponse(
    val message: String,
    val data: List<Daum>?,
    val count: Long,
)

data class Daum(
    val id: Long,
    val bot_identifier: String,
    val account_id: String,
    val project_id: String,
    val name: String,
    val user_id: String,
    val ui_preferences: UiPreferences,
    val persona: String,
    val timezone: String,
    val prompts: String,
    val suggested_replies: List<String>,
    val bot_ai_engine: Long,
    val supported_ai_name: String,
    val supported_ai_model_name: String,
    val profile_image: String,
    val knowledge_sources: List<KnowledgeSource>,
    val status: List<Status>,
    val created_at: String,
    val bot_type: String,
    val welcome_message: List<String>,
    val app_type: Long,
    val additional_instruction: String,
    val temperature: Double,
    val percentage: Long,
    val knowledge_base: Long,
    val functions: List<Long>,
    val workflows: Any?,
)

data class UiPreferences(
    val mode_theme: Int,
    val primary_color: String,
    val bot_bubble_color: String,
    val user_bubble_color: String,
    val font_size: String,
    val font_style: String,
    val bot_bubble_font_color: String,
    val user_bubble_font_color: String,
)

data class KnowledgeSource(
    val filename: String,
    val source: String,
    val addedOn: Long,
)

data class Status(
    val id: String,
    val timestamp: Long,
    val statusText: String,
)