package com.example.startups.models

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("title") var jobTitle: String,
    @SerializedName("company") val companyId: Int,
    @SerializedName("salary_from") var salaryFrom: Int,
    @SerializedName("salary_to")var salaryTo: Int,
    @SerializedName("is_favorited") val isFavorite: Boolean,
    var description: String,
)
