package com.tayari365.class10sciencenotes

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.*
import com.tayari365.class10sciencenotes.databinding.ActivityQuizLoaderBinding

class QuizLoaderActivity : BaseActivity() {

    private lateinit var binding: ActivityQuizLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityQuizLoaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color to colorPrimary
        window.statusBarColor = getColor(R.color.colorPrimary)

        // Set light icons (white) for dark status bar
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.isAppearanceLightStatusBars = false

        // Adjust toolbar height + top padding for Android 15+
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val defaultToolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height_with_status)
            view.layoutParams.height = statusBarHeight + defaultToolbarHeight
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        // Adjust WebView padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.webView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            insets
        }

        val quizPosition = intent.getIntExtra("QUIZ_POSITION", -1)
        val quizName = intent.getStringExtra("QUIZ_NAME") ?: "Chapter ${quizPosition + 1}"

        setupToolbar(quizName)
        loadQuiz(quizPosition)
    }

    private fun setupToolbar(quizName: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitle.text = quizName
        binding.backButton.setOnClickListener { finish() }
    }

    private fun loadQuiz(position: Int) {
        val quizFileName = "file:///android_asset/quiz_${position + 1}.html"
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Any page-specific logic after load
            }
        }
        binding.webView.loadUrl(quizFileName)
    }
}