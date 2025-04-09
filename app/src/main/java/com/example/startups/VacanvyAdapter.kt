package com.example.startups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.startups.databinding.JobElementBinding
import com.example.startups.models.Job

class VacanvyAdapter(): Adapter<VacanvyAdapter.ViewHolder>() {

    private val jobs: ArrayList<Job> = arrayListOf()

    fun setJobs(booksList: List<Job>) {
        println("Works")
        val diffResult = DiffUtil.calculateDiff(DiffCallback(jobs, booksList))
        jobs.clear()
        jobs.addAll(booksList)
        diffResult.dispatchUpdatesTo(this)
    }

    class DiffCallback(private val oldList: List<Job>, private val newList: List<Job>): DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = (oldList[oldItemPosition].jobTitle == newList[newItemPosition].jobTitle)

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = (oldList[oldItemPosition] == newList[newItemPosition])

    }

    class ViewHolder(private val binding: JobElementBinding): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(job: Job){
            with(binding){
                binding.jobTitle.text = job.jobTitle
                binding.companyName.text = job.companyName
                binding.description.text = job.description
                binding.salary.text = job.salary.toString()+"$"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            JobElementBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = jobs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(jobs[position])
    }
}