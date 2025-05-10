package com.example.startups.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.MyViewModel
import com.example.startups.R
import com.example.startups.adapters.VacanvyAdapter
import com.example.startups.api.APIRequests
import com.example.startups.api.ApiClient
import com.example.startups.databinding.FragmentHomeBinding
import com.example.startups.models.Access
import com.example.startups.models.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), APIRequests {

    private val adapter by lazy { VacanvyAdapter() }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val access = activity?.intent?.getStringExtra("access") ?: ""

        // Adapter және деректерді алу
        getVacancies(access, adapter)

        val recyclerView = binding.recommendRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val filtered = adapter.getJobs().filter { job ->
                    job.jobTitle.lowercase().contains(query.toString().lowercase())
                }
                adapter.setJobs(filtered)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}
