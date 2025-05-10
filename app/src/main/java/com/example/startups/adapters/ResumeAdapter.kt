package com.example.startups.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.databinding.ResumeElementBinding

class ResumeAdapter : RecyclerView.Adapter<ResumeAdapter.ViewHolder>() {

    private val infoList: MutableList<Triple<String, String, String>> = mutableListOf()

    fun setItems(info: String) {
        infoList.clear()
        info.split(",").forEach { item ->
            val parts = item.split("|&| ").map { it.trim() }
            if (parts.size == 3) {
                infoList.add(Triple(parts[0], parts[1], parts[2]))
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ResumeElementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Triple<String, String, String>) {
            binding.field.setText(item.first)
            binding.info.setText(item.second)
            binding.period.setText(item.third)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ResumeElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(infoList[position])
    }

    override fun getItemCount(): Int = infoList.size
}
