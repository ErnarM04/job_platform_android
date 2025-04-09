package com.example.startups.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.R
import com.example.startups.VacanvyAdapter
import com.example.startups.databinding.FragmentHomeBinding
import com.example.startups.models.Job

class HomeFragment : Fragment() {

    private val adapter by lazy {
        VacanvyAdapter()
    }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()

        val testList: List<Job> = listOf(Job("Android Developer (Junior)",
            "KazDev Solutions", 700, "We're looking for a junior Android developer with basic knowledge of Kotlin and Jetpack Compose. You'll work on internal client apps and receive mentorship from senior devs."),
            Job("Test", "Test", 100, "Test"),
            Job("Asslan", "Test", 100, "Test"),
            Job("Test", "Test", 100, "Test"),
            Job("Test", "Test", 100, "Test"))

        val recyclerView = view.findViewById<RecyclerView>(R.id.recommendRecycler)
        adapter.setJobs(
            booksList = testList.map { it }
        )
        recyclerView.adapter = adapter
    }

    private fun setupUI() {
        with(binding) {
            recommendRecycler.adapter = adapter
            recommendRecycler.layoutManager = LinearLayoutManager(context)
        }
    }

}