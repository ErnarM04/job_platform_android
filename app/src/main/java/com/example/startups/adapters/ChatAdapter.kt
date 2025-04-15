package com.example.startups.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.startups.activities.ChatActivity
import com.example.startups.databinding.ChatElementBinding
import com.example.startups.models.Chat

class ChatAdapter(): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val chatList: ArrayList<Chat> = arrayListOf()

    class ViewHolder(private val binding: ChatElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat){
            with(binding){
                chatDate.text = chat.time
                chatName.text = chat.user
                chatText.text = chat.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ChatElementBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatList[position])
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("receiver", chatList[position].user)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun addChat(chats: ArrayList<Chat>){
        chatList.addAll(chats)
    }
}