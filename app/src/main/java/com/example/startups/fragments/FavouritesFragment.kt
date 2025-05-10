package com.example.startups.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.R
import com.example.startups.adapters.VacanvyAdapter
import com.example.startups.api.ApiClient
import com.example.startups.models.Access
import com.example.startups.models.Job
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesFragment : Fragment() {

    private val adapter by lazy { VacanvyAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val refresh = activity?.intent?.getStringExtra("refresh").toString()
        var access: String = activity?.intent?.getStringExtra("access").toString()

        val json = """{
                    "refresh": "${refresh}"
                }""".trimIndent()
        val requestBody = json.toRequestBody("application/json".toMediaType())

        val instance = ApiClient.instance

        val refreshToken = instance.refresh(requestBody)
        refreshToken.enqueue(object: Callback<Access> {
            override fun onResponse(call: Call<Access>, response: Response<Access>) {
                println(response.raw())
                if (response.body()?.access?.isNotEmpty() == true){
                    access = response.body()!!.access
                    activity?.intent?.putExtra("access", access)
                    println(access)
                }
            }

            override fun onFailure(call: Call<Access>, t: Throwable) {
                println(t.message)
            }

        })
        val recyclerView = view.findViewById<RecyclerView>(R.id.favoritesRecycler)

        val request = instance.getVacancies("Bearer "+ access)
        request.enqueue(object : Callback<List<Job>> {
            override fun onResponse(call: Call<List<Job>>, response: Response<List<Job>>) {
                println(response.raw())
                val favoriteList: ArrayList<Job> = arrayListOf()
                response.body()?.forEach {
                    if(it.isFavorite){
                        favoriteList.add(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                println(t.message)
            }

        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

}