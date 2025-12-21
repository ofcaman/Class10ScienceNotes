package com.tayari365.class10sciencenotes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.ai.client.generativeai.Chat

class ChatViewModel(private val chat: Chat) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _messages.value += ChatMessage(message, isUser = true)
            val response = chat.sendMessage(message)
            _messages.value += ChatMessage(response.text ?: "Sorry, I couldn't generate a response.", isUser = false)
        }
    }
}