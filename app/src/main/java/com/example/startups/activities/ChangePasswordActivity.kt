package com.example.startups.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.startups.R
import com.example.startups.api.APIRequests

class ChangePasswordActivity: AppCompatActivity(), APIRequests {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        findViewById<Button>(R.id.passwordSave).setOnClickListener {

            val oldPassword = findViewById<TextView>(R.id.currentPassword).text.toString()
            val newPassword = findViewById<TextView>(R.id.newPassword).text.toString()
            val confirmPassword = findViewById<TextView>(R.id.confirmNewPassword).text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(baseContext, "Fill all fields!", Toast.LENGTH_LONG).show()
            }
            else {
                changePassword(
                    intent.getStringExtra("access").toString(),
                    oldPassword, newPassword, confirmPassword, baseContext
                )
            }
        }
    }

}