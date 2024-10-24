package io.isometrik.sample.chatbot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.isometrik.androidchatbot.ChatBotActivity
import io.isometrik.androidchatbot.data.model.Widget
import io.isometrik.androidchatbot.presentation.AiChatBotSdk
import io.isometrik.androidchatbot.presentation.listener.BotActionsListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        findViewById<FloatingActionButton>(R.id.flot_bot).setOnClickListener {
            val i = Intent(this,ChatBotActivity::class.java)
            startActivity(i)
//            finish()
        }

        AiChatBotSdk.instance?.addBotActionsListener(object : BotActionsListener{
            override fun onWidgetActionClick(widget: Widget) {
                Toast.makeText(this@MainActivity,"Click: ${widget.title}",Toast.LENGTH_LONG).show()
            }
        })
    }
}