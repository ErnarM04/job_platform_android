package com.example.startups.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.startups.MainActivity
import com.example.startups.R
import com.example.startups.api.ApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.example.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val signUp = findViewById<TextView>(R.id.signUpText)
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val button = findViewById<Button>(R.id.loginButton)
        button.setOnClickListener {
            val username = findViewById<TextView>(R.id.loginUser).text.toString()
            val password = findViewById<TextView>(R.id.loginPassword).text.toString()

            val json_token = """{"username":"${username}","password":"${password}"}""".trimIndent()
            val body = json_token.toRequestBody("application/json".toMediaType())
            val instance = ApiClient.instance
            val request = instance.getToken(body)
            request.enqueue(object : Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    println("Login request: "+response.code())
                    if (response.code() == 200){
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("access", response.body()?.access)
                        intent.putExtra("refresh", response.body()?.refresh)
                        intent.putExtra("isSignedIn", true)
                        intent.putExtra("username", username)
                        intent.putExtra("password", password)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login or password is incorrect.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    println(t.message)
                }

            })
        }

    }
}