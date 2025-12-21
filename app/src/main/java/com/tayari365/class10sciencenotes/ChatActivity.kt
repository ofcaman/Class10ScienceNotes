package com.tayari365.class10sciencenotes

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch
import java.util.Locale

class ChatActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: ChatAdapter
    private lateinit var messageInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var micButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var rootLayout: View
    private var isMicActive = false

    private val GEMINI_API_KEY = "AIzaSyD9kCSoFamRtbQt0w2ZpS637d85ySr7JcM"

    companion object {
        private const val SPEECH_REQUEST_CODE = 0
    }

    private val chapters = mapOf(
        1 to "Scientific Study",
        2 to "Classification of Living Beings",
        3 to "Honey Bee",
        4 to "Heredity",
        5 to "Physiological Structure And Life Processes",
        6 to "Nature and Environment",
        7 to "Force and Motion",
        8 to "Pressure",
        9 to "Heat",
        10 to "Wave",
        11 to "Electricity and Magnetism",
        12 to "Universe",
        13 to "Information and Communication Technology",
        14 to "Classification of Elements",
        15 to "Chemical Reaction",
        16 to "Gas",
        17 to "Metal and Non-Metal",
        18 to "Hydrocarbon and its Compounds",
        19 to "Chemicals Used in Daily Life"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Views
        rootLayout = findViewById(R.id.rootLayout)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        micButton = findViewById(R.id.micButton)
        recyclerView = findViewById(R.id.chatRecyclerView)
        val backButton = findViewById<ImageView>(R.id.ic_back)
        val toolBar = findViewById<MaterialToolbar>(R.id.toolbar)

        // Toolbar
        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        window.statusBarColor = getColor(R.color.colorPrimary)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.isAppearanceLightStatusBars = false

        ViewCompat.setOnApplyWindowInsetsListener(toolBar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val defaultToolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height_with_status)
            view.layoutParams.height = statusBarHeight + defaultToolbarHeight
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        // RecyclerView
        adapter = ChatAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Edge-to-edge with keyboard handling
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())

            // Add bottom padding to root layout so input field moves above keyboard
            view.updatePadding(bottom = imeInsets.bottom + navInsets.bottom)

            // Scroll to latest message if keyboard is visible
            if (imeInsets.bottom > 0) {
                recyclerView.scrollToPosition(adapter.itemCount - 1)
            }

            insets
        }

        // AI Chat setup
        val chatHistory = listOf(
            content("user") {
                text(
                    """
                    Your name is GyaniDai. You are a helpful, knowledgeable elder-brother-like teacher who tutors Class 10 students in Science for the Nepal NEB exam.
                    Only provide concise, syllabus-oriented answers.
                    Always use chapter context if provided.
                    """.trimIndent()
                )
            }
        )

        val model = GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = GEMINI_API_KEY,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 40
                topP = 0.95f
                maxOutputTokens = 8192
                responseMimeType = "text/plain"
            }
        )

        val chat = model.startChat(chatHistory)
        val factory = ChatViewModelFactory(chat)
        viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        sendButton.setOnClickListener {
            if (isInternetAvailable()) sendMessage()
            else Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }

        micButton.setOnClickListener { toggleMic() }

        // Collect messages from ViewModel
        lifecycleScope.launch {
            viewModel.messages.collect { messages ->
                adapter.updateMessages(messages)
                recyclerView.scrollToPosition(messages.size - 1)
            }
        }
    }

    private fun toggleMic() {
        if (isMicActive) {
            micButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_mic_inactive))
        } else {
            micButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_mic_active))
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            }
            try {
                startActivityForResult(intent, SPEECH_REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(this, "Speech recognition not available", Toast.LENGTH_SHORT).show()
            }
        }
        isMicActive = !isMicActive
    }

    private fun sendMessage() {
        val message = messageInput.text.toString().trim()
        if (message.isEmpty()) return

        val chapterNumber = extractChapterFromMessage(message)
        val chapterContent = chapterNumber?.let { chapters[it] } ?: ""

        val userMessage = """
            ${chapterNumber ?: " "} $chapterContent
            $message
        """.trimIndent()

        try {
            viewModel.sendMessage(userMessage)
        } catch (e: Exception) {
            Toast.makeText(this, "AI request failed: ${e.message}", Toast.LENGTH_LONG).show()
        }

        messageInput.text.clear()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(messageInput.windowToken, 0)
    }

    private fun extractChapterFromMessage(message: String): Int? {
        val regex = Regex("""\b(?:chapter|chap|ch)\s*(\d+)\b""", RegexOption.IGNORE_CASE)
        val match = regex.find(message)
        return match?.groups?.get(1)?.value?.toIntOrNull()
    }

    private fun isInternetAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val net = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(net) ?: return false
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { it[0] }
            spokenText?.let {
                messageInput.setText(it)
                if (isInternetAvailable()) sendMessage()
                else Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
