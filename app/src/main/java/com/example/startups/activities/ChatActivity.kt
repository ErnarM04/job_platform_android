package com.example.startups.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.startups.R
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
    }
}