package com.example.startups.models

import android.telephony.PhoneNumberUtils
import java.util.Date

data class User(
    var fullName: String,
    var email: String,
    var phoneNumber: PhoneNumberUtils,
    var birthday: Date,
    var job: String
)
