package com.example.startups.api

import android.content.Context
import android.widget.Toast
import com.example.startups.adapters.VacanvyAdapter
import com.example.startups.models.Access
import com.example.startups.models.Application
import com.example.startups.models.Company
import com.example.startups.models.Job
import com.example.startups.models.Profile
import com.example.startups.models.Resume
import com.google.android.gms.common.api.Api
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface APIRequests {
    fun refresh(refresh: String): String {
        val json = """{
                    "refresh": "${refresh}"
                }""".trimIndent()
        val requestBody = json.toRequestBody("application/json".toMediaType())

        var access: String = ""
        val instance = ApiClient.instance

        val refreshToken = instance.refresh(requestBody)
        refreshToken.enqueue(object: Callback<Access>{
            override fun onResponse(call: Call<Access>, response: Response<Access>) {
                println(response.raw())
                access = response.body()?.access.toString()

            }

            override fun onFailure(call: Call<Access>, t: Throwable) {
                println(t.message)
            }

        })
        return access
    }

    fun getVacancies(access: String, adapter: VacanvyAdapter) {
        val instance = ApiClient.instance
        val request = instance.getVacancies("Bearer " + access)

        request.enqueue(object : Callback<List<Job>> {
            override fun onResponse(call: Call<List<Job>>, response: Response<List<Job>>) {
                if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                    val jobList = response.body()!!
                    adapter.setJobs(jobList)  // Adapter-ге деректерді беру
                    println(jobList)
                }
            }

            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    fun sendApplication(vacancyId: Int, coverLetter: String, access: String, context: Context): Application {
        val json = """{
                    "vacancy": "${vacancyId}",
                    "cover_letter": "${coverLetter}"
                }""".trimIndent()
        val requestBody = json.toRequestBody("application/json".toMediaType())

        var applicationInfo: Application = Application(0, 0, "", "")

        val instance = ApiClient.instance
        val request = instance.sendApplication(access, requestBody)
        request.enqueue(object : Callback<Application>{
            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                if (response.body() != null){
                    applicationInfo = response.body()!!
                    Toast.makeText(context, "Application Sent Successfully!", Toast.LENGTH_LONG).show()
                }
                println(response.raw())
            }

            override fun onFailure(call: Call<Application>, t: Throwable) {
                println(t.message)
            }
        })

        return applicationInfo
    }

    fun getResumes(access: String): Resume {

        var resume: Resume = Resume(0,"", "","","")

        val instance = ApiClient.instance
        val request = instance.getResumes("Bearer "+access)

        request.enqueue(object : Callback<List<Resume>>{
            override fun onResponse(call: Call<List<Resume>>, response: Response<List<Resume>>) {
                if (response.body() != null) {
                    resume = response.body()!![0]
                    println(response.body()!![0])
                }
                println(response.raw())
            }

            override fun onFailure(call: Call<List<Resume>>, t: Throwable) {
                println(t.message)
            }
        })
        return resume
    }

    fun editProfile(username: String, phone: String, email: String, access: String) {
        val json_token = """{
                        "user": {
                            "username": "${username}",
                            "phone": "${phone}",
                            "email": "${email}"
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
    }

    fun getCompanyInfo(companyId: Int): Company {

        var company = Company(
            id = 0,
            name = "",
            description = "",
            industry = "",
            website = "",
            logo = "",
            foundedYear = ""
        )

        val instance = ApiClient.instance
        val request = instance.getCompanyInfo(companyId)
        request.enqueue(object : Callback<Company>{
            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                if (response.body() != null){
                    println(response.raw())
                    company = response.body()!!
                }
            }

            override fun onFailure(call: Call<Company>, t: Throwable) {
                println(t.message)
            }
        })

        return company
    }

    fun changePassword(access: String, oldPassword: String, newPassword: String, confirmPassword: String, context: Context){
        val json = """{
                    "old_password": "${oldPassword}",
                    "new_password": "${newPassword}",
                    "new_password_confirm": "${confirmPassword}"
                }""".trimIndent()
        val requestBody = json.toRequestBody("application/json".toMediaType())

        val instance = ApiClient.instance

        val request = instance.changePassword(access, requestBody)
        request.enqueue(object: Callback<com.example.startups.models.Response>{
            override fun onResponse(call: Call<com.example.startups.models.Response>, response: Response<com.example.startups.models.Response>) {
                println(response.raw())
                Toast.makeText(context, response.body()?.detail, Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<com.example.startups.models.Response>, t: Throwable) {
                println(t.message)
            }

        })
    }
}