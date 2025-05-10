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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.MyViewModel
import com.example.startups.activities.ChangePasswordActivity
import com.example.startups.R
import com.example.startups.adapters.ResumeAdapter
import com.example.startups.api.APIRequests
import com.example.startups.api.ApiClient
import com.example.startups.models.Access
import com.example.startups.models.Profile
import com.example.startups.models.Resume
import com.google.android.gms.common.api.Api
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), APIRequests {

    private val educationAdapter by lazy { ResumeAdapter() }
    private val experienceAdapter by lazy { ResumeAdapter() }

    private lateinit var fullNameEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var phoneEdit: EditText
    private lateinit var birthdayEdit: EditText
    private lateinit var jobEdit: EditText
    private lateinit var editButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var logoutButton: Button

    private var accessToken: String = ""
    private var refreshToken: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Токендерді алу
        accessToken = activity?.intent?.getStringExtra("access").orEmpty()
        refreshToken = activity?.intent?.getStringExtra("refresh").orEmpty()

        // UI элементтерін байланыстыру
        fullNameEdit = view.findViewById(R.id.fullName)
        emailEdit = view.findViewById(R.id.email)
        phoneEdit = view.findViewById(R.id.phoneNumber)
        birthdayEdit = view.findViewById(R.id.birthday)
        jobEdit = view.findViewById(R.id.job)
        editButton = view.findViewById(R.id.editButton)
        changePasswordButton = view.findViewById(R.id.changePasswordEnter)
        logoutButton = view.findViewById(R.id.logoutButton)

        // RecyclerView орнату
        val educationRecycler = view.findViewById<RecyclerView>(R.id.educationRecycler)
        val experienceRecycler = view.findViewById<RecyclerView>(R.id.experienceRecycler)

        educationRecycler.adapter = educationAdapter
        experienceRecycler.adapter = experienceAdapter
        educationRecycler.layoutManager = LinearLayoutManager(context)
        experienceRecycler.layoutManager = LinearLayoutManager(context)

        // Деректерді жүктеу
        loadProfile()
        loadResume()

        setupButtons()

        return view
    }

    private fun loadProfile() {
        ApiClient.instance.getProfile("Bearer $accessToken")
            .enqueue(object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    response.body()?.user?.let { user ->
                        fullNameEdit.setText(user.fullName)
                        emailEdit.setText(user.email)
                        phoneEdit.setText(user.phoneNumber)
                    }
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    println("Profile load error: ${t.message}")
                }
            })
    }

    private fun loadResume() {
        val instance = ApiClient.instance
        val request = instance.getResumes("Bearer $accessToken")
        request.enqueue(object : Callback<List<Resume>> {
                override fun onResponse(call: Call<List<Resume>>, response: Response<List<Resume>>) {
                    response.body()?.get(0)?.let { resume ->
                        jobEdit.setText(resume.title)
                        educationAdapter.setItems(resume.education)
                        experienceAdapter.setItems(resume.experience) }
                }

                override fun onFailure(call: Call<List<Resume>>, t: Throwable) {
                    println("Resume load error: ${t.message}")
                }
            })
    }

    private fun setupButtons() {
        editButton.setOnClickListener {
            val isEditing = editButton.text == "Edit"
            setEditingEnabled(isEditing)

            if (!isEditing) {
                // Save data
                editProfile(
                    fullNameEdit.text.toString(),
                    phoneEdit.text.toString(),
                    emailEdit.text.toString(),
                    accessToken
                )
            }

            editButton.text = if (isEditing) "Save" else "Edit"
        }

        changePasswordButton.setOnClickListener {
            val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
            intent.putExtra("access", accessToken)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            val intent = activity?.intent
            intent?.putExtra("isSignedIn", false)
            intent?.putExtra("access", "")
            intent?.putExtra("refresh", "")
            activity?.finish()
        }
    }

    private fun setEditingEnabled(enabled: Boolean) {
        fullNameEdit.isEnabled = enabled
        emailEdit.isEnabled = enabled
        phoneEdit.isEnabled = enabled
        birthdayEdit.isEnabled = enabled
        jobEdit.isEnabled = enabled
    }
}
