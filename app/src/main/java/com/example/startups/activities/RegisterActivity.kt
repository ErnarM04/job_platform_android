package com.example.startups.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.startups.MainActivity
import com.example.startups.R
import com.example.startups.api.ApiClient
import com.example.startups.models.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.example.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.typeOf

class RegisterActivity : AppCompatActivity() {

    private var isSignedUp = false

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        val button = findViewById<Button>(R.id.registerButton)
        val instance = ApiClient.instance
        val login = findViewById<TextView>(R.id.textView11)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        button.setOnClickListener {
            val name = findViewById<TextView>(R.id.registerName).text.toString()
            val email = findViewById<TextView>(R.id.registerEmail).text.toString()
            val password = findViewById<TextView>(R.id.registerPassword).text.toString()
            val verify = findViewById<TextView>(R.id.registerConfirmPassword).text.toString()
            val phone = findViewById<TextView>(R.id.registerPhoneNumber).text.toString()
            val user = User(
                fullName = name, email = email, phoneNumber = phone, birthday = null,
                job = null.toString()
            )
            if (verify == password && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && verify.isNotEmpty() && phone.isNotEmpty()) {
                if("@" !in email || "." !in email || (email.indexOf('.') >= (email.length-2))){
                    Toast.makeText(this, "Email field is incorrect.", Toast.LENGTH_LONG).show()
                }
                else if (!isValidPassword(password)){
                    Toast.makeText(this, "Password field is incorrect.", Toast.LENGTH_LONG).show()
                }
                else {
                    val json = """{
                    "username": "${name}",
                    "password": "${password}",
                    "email": "${email}",
                    "phone": "${phone}"
                }""".trimIndent()
                    println(json)
                    val requestBody = json.toRequestBody("application/json".toMediaType())
                    val request = instance.register(requestBody)
                    request.enqueue(object : Callback<User>{
                        override fun onResponse(call: Call<User>, response: Response<User>) {
                            println(response.raw())
                            isSignedUp = true
                        }

                        override fun onFailure(call: Call<User>, t: Throwable) {
                            println(t.message)
                        }

                    })


                    val json_token = """{"username":"${name}","password":"${password}"}""".trimIndent()
                    val body = json_token.toRequestBody("application/json".toMediaType())
                    if(isSignedUp){
                        val instance = ApiClient.instance
                        val token = instance.getToken(body)
                        token.enqueue(object : Callback<TokenResponse>{
                            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                                println(response.raw())
                                println(response.body()?.access?.javaClass?.simpleName)
                                val intent = Intent(baseContext, MainActivity::class.java)
                                intent.putExtra("access", response.body()?.access)
                                intent.putExtra("refresh", response.body()?.refresh)
                                intent.putExtra("username", name)
                                intent.putExtra("password", password)
                                intent.putExtra("isSignedIn", true)
                                startActivity(intent)
                                finish()
                            }

                            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                                println(t.message)
                            }

                        })
                    }


                }
            }
            else {
                if (password != verify){
                    Toast.makeText(this, "Passwords didn't match", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Complete every field.", Toast.LENGTH_LONG).show()
                }

            }

        }
    }
}