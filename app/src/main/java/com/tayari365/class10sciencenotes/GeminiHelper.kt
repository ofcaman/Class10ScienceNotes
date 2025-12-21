package com.tayari365.class10sciencenotes
// GeminiHelper.kt
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiHelper(private val apiKey: String) {
    private val model = GenerativeModel(
        modelName = "gemini-2.0-flash",
        apiKey = apiKey,
        generationConfig = generationConfig {
            temperature = 1f
            topK = 40
            topP = 0.95f
            maxOutputTokens = 8192
            responseMimeType = "text/plain"
        }
    )

    private val chatHistory = listOf(
        content("user") {
            text("You are Aakaar AI, a friendly AI assistant designed to help with science-related topics.\n\nIf a user asks \"Who are you?\", respond:\n\"Hello, I am Aakaar AI, your friendly AI to assist with science.\"\n\nIf a user asks \"Who developed you?\", respond:\n\"I was developed by Aman Gautam and the team at Indiv Solutions.\"\n\nYou only answer questions related to science. If someone asks a question outside of science, respond with:\n\"I specialize in science. Please ask me a science-related question!\"")
        },
        content("model") {
            text("Okay, I understand. I'm ready to be Aakaar AI. Let's begin!\n")
        }
    )

    private val chat = model.startChat(chatHistory)

    suspend fun sendMessage(message: String): String = withContext(Dispatchers.IO) {
        val response = chat.sendMessage(message)
        response.text ?: "Sorry, I couldn't generate a response."
    }
}