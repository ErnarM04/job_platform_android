package com.example.startups.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.R
import com.example.startups.adapters.ChatAdapter
import com.example.startups.models.Chat

class ChatFragment : Fragment() {

    private val adapter by lazy {
        ChatAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val chatList: ArrayList<Chat> = arrayListOf(Chat("AI Assistant", null, null, null),
            Chat("Yernar", "00:00", "Example", null))
        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecycler)
        adapter.addChat(chatList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)


        return view
    }

}