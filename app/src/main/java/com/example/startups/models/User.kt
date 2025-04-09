package com.example.startups.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class User(
    @SerializedName("username") var fullName: String,
    var email: String,
    @SerializedName("phone") var phoneNumber: String,
    var birthday: Date?,
    var job: String
)
