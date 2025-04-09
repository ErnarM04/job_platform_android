package com.example.startups

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.startups.activities.RegisterActivity
import com.example.startups.api.ApiClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.MediaType.Companion.toMediaType
import org.example.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {

        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        var intent = getIntent()
        var isSignedIn = intent.getBooleanExtra("isSignedIn", false)
        var access = intent.getStringExtra("access")
        var refresh = intent.getStringExtra("refresh")
        var username = intent.getStringExtra("username")
        var password = intent.getStringExtra("password")

        viewModel.setSignedIn(isSignedIn)
        if (access != null) {
            viewModel.setAccessToken(access)
        }
        if (refresh != null) {
            viewModel.setRefreshToken(refresh)
        }
        if (username != null && password != null) {
            viewModel.setUser(username, password)
        }

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationBar.setupWithNavController(navController)


        bottomNavigationBar.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.profileFragment -> {
                    if (isSignedIn) {
                        navController.navigate(R.id.profileFragment)
                    } else {
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                    }
                    true
                }

                else -> {navController.navigate(item.itemId)
                    true}
            }
        }
        super.onResume()
    }
}