package com.example.startups.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.startups.R

class JobInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_job_info)
        val intent = intent
        findViewById<TextView>(R.id.textView4).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.textView7).text = intent.getStringExtra("description")
        findViewById<TextView>(R.id.textView8).text = intent.getStringExtra("company")


    }
}