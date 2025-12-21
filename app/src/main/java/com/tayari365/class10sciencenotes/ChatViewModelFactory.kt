package com.tayari365.class10sciencenotes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ai.client.generativeai.Chat

class ChatViewModelFactory(private val chat: Chat) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(chat) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}