package com.example.startups.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.startups.MyViewModel
import com.example.startups.activities.ChangePasswordActivity
import com.example.startups.R
import com.example.startups.api.ApiClient
import com.example.startups.models.Access
import com.example.startups.models.Profile
import com.google.android.gms.common.api.Api
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        var access: String = ""
        var refresh: String = ""

        viewModel.access.observe(viewLifecycleOwner, Observer { accessToken -> access = accessToken })
        viewModel.refresh.observe(viewLifecycleOwner, Observer { refreshToken -> refresh = refreshToken })

        access = activity?.intent?.getStringExtra("access").toString()

        val instance = ApiClient.instance
        val request = instance.getProfile("Bearer "+access)
        println("Bearer "+access)
        request.enqueue(object : Callback<Profile>{
            override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                val user = response.body()?.user
                println(user)
                val name = view.findViewById<TextView>(R.id.fullName)
                val email = view.findViewById<TextView>(R.id.email)
                val phone = view.findViewById<TextView>(R.id.phoneNumber)
                name.text = user?.fullName
                email.text = user?.email
                phone.text = user?.phoneNumber
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                println(t.message)
            }

        })

        val json_token = """{"refresh":"${refresh}"}""".trimIndent()
        val body = json_token.toRequestBody("application/json".toMediaType())

        val refreshRequest = instance.refresh(body)
        refreshRequest.enqueue(object : Callback<Access>{
            override fun onResponse(call: Call<Access>, response: Response<Access>) {
                response.body()?.access?.let { activity?.intent?.putExtra("access", it) }
                println(response.raw())
            }

            override fun onFailure(call: Call<Access>, t: Throwable) {
                println(t.message)
            }

        })

        val changePassword = view.findViewById<Button>(R.id.changePasswordEnter)
        val editButton = view.findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.fullName)
            val email = view.findViewById<EditText>(R.id.email)
            val phone = view.findViewById<EditText>(R.id.phoneNumber)
            val birthday = view.findViewById<EditText>(R.id.birthday)
            val job = view.findViewById<EditText>(R.id.job)
            when (editButton.text){
                "Edit" -> {
                    username.isEnabled = true
                    email.isEnabled = true
                    phone.isEnabled = true
                    birthday.isEnabled = true
                    job.isEnabled = true

                    editButton.text = "Save"
                }
                "Save" -> {
                    username.isEnabled = false
                    email.isEnabled = false
                    phone.isEnabled = false
                    birthday.isEnabled = false
                    job.isEnabled = false
                    val json_token = """{
                        "user": {
                            "username": "${username.text}",
                            "phone": "${phone.text}",
                            "email": "${email.text}"
                            }
                        }""".trimIndent()
                    val body = json_token.toRequestBody("application/json".toMediaType())
                    val instance = ApiClient.instance
                    val request = instance.editProfile("Bearer "+access, body)
                    request.enqueue(object : Callback<Profile>{
                        override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                            println("Profile edited.")
                            println(response.raw())
                        }

                        override fun onFailure(call: Call<Profile>, t: Throwable) {
                            println(t.message)
                        }

                    })
                    editButton.text = "Edit"
                }
            }
        }
        changePassword.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}