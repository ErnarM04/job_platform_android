package com.example.startups.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.startups.R
import com.example.startups.api.AIClient
import com.example.startups.models.AIMessage
import com.example.startups.models.DeepSeekRequest
import com.example.startups.models.DeepSeekResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)
        val intent = intent
        findViewById<TextView>(R.id.textView14).text = intent.getStringExtra("receiver")
        val instance = AIClient.instance
        val messages = listOf(
            AIMessage("user", "Сәлем! Маған жоспар құрып берші.")
        )
        val request = instance.askDeepSeek(DeepSeekRequest(messages=messages), "Bearer sk-00674091841c4a538324b62d9d70ca5f")
        request.enqueue(object : Callback<DeepSeekResponse>{
            override fun onResponse(
                call: Call<DeepSeekResponse>,
                response: Response<DeepSeekResponse>
            ) {
                val reply = response.body()?.choices?.firstOrNull()?.message?.content
                println(reply ?: "Жауап табылмады")
                println(response.raw())
            }

            override fun onFailure(call: Call<DeepSeekResponse>, t: Throwable) {
                println(t.message)
            }
        })
    }
}