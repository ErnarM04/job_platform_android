package com.example.startups.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.startups.R
import com.example.startups.api.APIRequests

class JobInfoActivity : AppCompatActivity(), APIRequests {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_job_info)
        val intent = intent
        var access: String = intent.getStringExtra("access").toString()
        access = refresh(intent.getStringExtra("refresh").toString())
        val refresh = intent.getStringExtra("refresh").toString()
        intent.putExtra("access", refresh(refresh))
        println("Access:"+access)
        findViewById<TextView>(R.id.textView4).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.textView7).text = intent.getStringExtra("description")
        findViewById<Button>(R.id.button3).setOnClickListener {
            val id = intent.getIntExtra("id", 0)
            if (id != null) {
                sendApplication(
                    id,
                    coverLetter = "",
                    access = "Bearer "+access,
                    this
                )
            }
        }

        val company = getCompanyInfo(intent.getIntExtra("company", 0))
        println(company)
        findViewById<TextView>(R.id.compName).text = company.name
        val companyLogo = findViewById<ImageView>(R.id.compLogo)
        Glide.with(this).load(company.logo).into(companyLogo)

        findViewById<ConstraintLayout>(R.id.company).setOnClickListener {
            val intent = Intent(this, CompanyInfoActivity::class.java)
            intent.putExtra("company", company.id)
            intent.putExtra("refresh", refresh)
            startActivity(intent)
        }

    }
}