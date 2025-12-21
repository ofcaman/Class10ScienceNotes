package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityMockTestLoaderBinding

class MockTestLoaderActivity : BaseActivity() {

    private lateinit var binding: ActivityMockTestLoaderBinding

    private val topics = listOf(
        McqTopic(1, "Scientific Study"),
        McqTopic(2, "Classification of Living Beings"),
        McqTopic(3, "Life Cycle"),
        McqTopic(4, "Heredity"),
        McqTopic(5, "Physiological Structure and Life Process"),
        McqTopic(6, "Nature and Environment"),
        McqTopic(7, "Force and Motion"),
        McqTopic(8, "Pressure"),
        McqTopic(9, "Heat"),
        McqTopic(10, "Wave"),
        McqTopic(11, "Electricity and Magnetism"),
        McqTopic(12, "The Universe"),
        McqTopic(13, "ICT"),
        McqTopic(14, "Classification of Elements"),
        McqTopic(15, "Chemical Reactions"),
        McqTopic(16, "Gases"),
        McqTopic(17, "Metals"),
        McqTopic(18, "Hydrocarbons and Their Compounds"),
        McqTopic(19, "Materials Used in Daily Life")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMockTestLoaderBinding.inflate(layoutInflater)
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

        // Adjust RecyclerView padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            insets
        }

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.toolbarTitle.text = "Mock Test"
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = McqTopicsAdapter(topics) { topicId, position ->
            navigateToQuizLoaderActivity(topicId, position)
        }
    }

    private fun navigateToQuizLoaderActivity(topicId: Int, position: Int) {
        val intent = Intent(this, QuizLoaderActivity::class.java).apply {
            putExtra("QUIZ_POSITION", position)
            putExtra("QUIZ_NAME", topics[position].title)
        }
        startActivity(intent)
    }
}