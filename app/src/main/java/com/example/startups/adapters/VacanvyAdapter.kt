package com.example.startups.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.startups.R
import com.example.startups.activities.JobInfoActivity
import com.example.startups.api.APIRequests
import com.example.startups.databinding.JobElementBinding
import com.example.startups.models.Job

class VacanvyAdapter : RecyclerView.Adapter<VacanvyAdapter.ViewHolder>(), APIRequests {

    private val jobs: MutableList<Job> = mutableListOf()
    private var access: String = ""
    private var refresh: String = ""

    fun setJobs(jobsList: List<Job>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(jobs, jobsList))
        jobs.clear()
        jobs.addAll(jobsList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun getJobs() = jobs

    fun setAccess(access: String, refresh: String) {
        this.access = access
        this.refresh = refresh
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JobElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = jobs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobs[position]
        holder.bind(job)

        holder.binding.sendRequest.setOnClickListener {
            val context = holder.itemView.context
            sendApplication(job.id, "", "Bearer $access", context)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, JobInfoActivity::class.java).apply {
                putExtra("access", access)
                putExtra("refresh", refresh)
                putExtra("id", job.id)
                putExtra("title", job.jobTitle)
                putExtra("company", job.companyId)
                putExtra("description", job.description)
                putExtra("salary", "${job.salaryFrom}-${job.salaryTo}")
            }
            context.startActivity(intent)
        }
    }

    class ViewHolder(val binding: JobElementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            with(binding) {
                jobTitle.text = job.jobTitle
                description.text = job.description
                salary.text = "${job.salaryFrom}-${job.salaryTo}"
                // companyName.text = job.companyName // Егер Job ішінде companyName бар болса
            }
        }
    }

    class DiffCallback(
        private val oldList: List<Job>,
        private val newList: List<Job>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

