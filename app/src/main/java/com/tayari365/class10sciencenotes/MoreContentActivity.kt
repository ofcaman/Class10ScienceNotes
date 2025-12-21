package com.tayari365.class10sciencenotes

import android.os.Bundle
import androidx.core.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityMoreContentBinding

class MoreContentActivity : BaseActivity() {

    private lateinit var binding: ActivityMoreContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMoreContentBinding.inflate(layoutInflater)
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
            finish()
        }

        binding.toolbarTitle.text = "All Chapters"
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        val chapters = listOf(
            Chapter("10Set Book", R.drawable.tensetbook),
            Chapter("Formula", R.drawable.formulas),
            Chapter("Mind Maps", R.drawable.mindmaps),
            Chapter("Imp Question", R.drawable.impquestions),
            Chapter("PYPS", R.drawable.pyps),
            Chapter("Model Questions", R.drawable.pyqs),
            Chapter("Mock Test", R.drawable.mocktest),
            Chapter("Aakaar Ai", R.drawable.nova_ai)
        )
        binding.recyclerView.adapter = MoreContentAdapter(chapters, this)
    }
}

