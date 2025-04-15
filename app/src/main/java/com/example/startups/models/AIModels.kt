package com.example.startups.models

data class AIMessage(val role: String, val content: String)

data class DeepSeekRequest(
    val model: String = "deepseek-chat",
    val messages: List<AIMessage>
)

data class DeepSeekResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: AIMessage
)