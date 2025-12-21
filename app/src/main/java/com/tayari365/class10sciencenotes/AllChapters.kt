package com.tayari365.class10sciencenotes

import android.os.Bundle
import android.util.Log
import androidx.core.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityAllChaptersBinding

class AllChapters : BaseActivity() {

    private lateinit var binding: ActivityAllChaptersBinding
    private lateinit var adapter: MoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityAllChaptersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color to colorPrimary with fallback
        try {
            val color = getColor(R.color.colorPrimary)
            window.statusBarColor = color
            Log.d("AllChapters", "Status bar color set to: $color")
        } catch (e: Exception) {
            Log.e("AllChapters", "Failed to set status bar color: ${e.message}")
            window.statusBarColor = android.graphics.Color.BLACK // Fallback color
        }

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
        binding.toolbarTitle.text = "All Chapters"
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        val chapters = listOf(
            Chapter("Chapter 1", R.drawable.note),
            Chapter("Chapter 2", R.drawable.note),
            Chapter("Chapter 3", R.drawable.note),
            Chapter("Chapter 4", R.drawable.note),
            Chapter("Chapter 5", R.drawable.note),
            Chapter("Chapter 6", R.drawable.note),
            Chapter("Chapter 7", R.drawable.note),
            Chapter("Chapter 8", R.drawable.note),
            Chapter("Chapter 9", R.drawable.note),
            Chapter("Chapter 10", R.drawable.note),
            Chapter("Chapter 11", R.drawable.note),
            Chapter("Chapter 12", R.drawable.note),
            Chapter("Chapter 13", R.drawable.note),
            Chapter("Chapter 14", R.drawable.note),
            Chapter("Chapter 15", R.drawable.note),
            Chapter("Chapter 16", R.drawable.note),
            Chapter("Chapter 17", R.drawable.note),
            Chapter("Chapter 18", R.drawable.note),
            Chapter("Chapter 19", R.drawable.note)
        )
        adapter = MoreAdapter(chapters)
        binding.recyclerView.adapter = adapter
    }
}

