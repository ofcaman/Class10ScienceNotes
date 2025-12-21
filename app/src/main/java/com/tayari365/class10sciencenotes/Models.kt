package com.tayari365.class10sciencenotes

import com.google.gson.annotations.SerializedName

data class CompletionRequest(
    @SerializedName("model") val model: String,
    @SerializedName("prompt") val prompt: String,
    @SerializedName("max_tokens") val maxTokens: Int,
    @SerializedName("temperature") val temperature: Double = 0.7
)

data class CompletionResponse(
    @SerializedName("choices") val choices: List<Choice>
)

data class Choice(
    @SerializedName("text") val text: String
)

data class Message(
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)