package com.tayari365.class10sciencenotes

import android.os.Bundle
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityPypBinding
import kotlin.random.Random

class PypActivity : BaseActivity() {

    private lateinit var binding: ActivityPypBinding
    private lateinit var adapter: PypsAdapter

    private val colors = listOf(
        "#FF0000", // Red
        "#FFD700", // Yellow
        "#0000FF", // Blue
        "#4B0082", // Indigo
        "#8A2BE2", // Violet
        "#87CEEB"  // Sky Blue
    ).map { android.graphics.Color.parseColor(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityPypBinding.inflate(layoutInflater)
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

        binding.toolbarTitle.text = "Past Year Papers"
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val pypsList = (2079..2080).mapIndexed { index, year ->
            PyqItem(
                number = index + 1,
                name = "Past Year Question $year",
                backgroundColor = getRandomColor()
            )
        }
        adapter = PypsAdapter(pypsList)
        binding.recyclerView.adapter = adapter
    }

    private fun getRandomColor(): Int {
        return colors[Random.nextInt(colors.size)]
    }
}