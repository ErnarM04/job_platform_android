package com.example.startups.models

import java.net.URI

data class Chat(
    val user: String,
    val time: String?,
    val text: String?,
    val image: URI?
)
