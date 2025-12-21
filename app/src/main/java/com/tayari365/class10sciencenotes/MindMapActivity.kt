package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar

class MindMapActivity : BaseActivity() {

    private val mindMaps = listOf(
        MindMap(1, "Scientific Study"),
        MindMap(2, "Classification of Living Beings"),
        MindMap(3, "Life Cycle"),
        MindMap(4, "Heredity"),
        MindMap(5, "Physiological Structure and Life Process"),
        MindMap(6, "Nature and Environment"),
        MindMap(7, "Force and Motion"),
        MindMap(8, "Pressure"),
        MindMap(9, "Heat"),
        MindMap(10, "Wave"),
        MindMap(11, "Electricity and Magnetism"),
        MindMap(12, "The Universe"),
        MindMap(13, "ICT"),
        MindMap(14, "Classification of Elements"),
        MindMap(15, "Chemical Reactions"),
        MindMap(16, "Gases"),
        MindMap(17, "Metals"),
        MindMap(18, "Hydrocarbons and Their Compounds"),
        MindMap(19, "Materials Used in Daily Life")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mind_map)

        // Set status bar color to colorPrimary
        window.statusBarColor = getColor(R.color.colorPrimary)

        // Set light icons (white) for dark status bar
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.isAppearanceLightStatusBars = false

        // Adjust toolbar height + top padding for Android 15+
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<MaterialToolbar>(R.id.toolbar)) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val defaultToolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height_with_status)
            view.layoutParams.height = statusBarHeight + defaultToolbarHeight
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        // Adjust RecyclerView padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerView)) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            insets
        }

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<TextView>(R.id.toolbarTitle).text = "Mind Maps"
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MindMapAdapter(mindMaps) { mindMapId, position ->
            navigateToNotesViewerActivity(mindMapId, position)
        }
    }

    private fun navigateToNotesViewerActivity(mindMapId: Int, position: Int) {
        val intent = Intent(this, NotesViewerActivity::class.java).apply {
            putExtra("SUBJECT_ID", "mind_map")
            putExtra("POSITION", position)
            putExtra("MIND_MAP_NAME", mindMaps[position].title)
        }
        startActivity(intent)
    }
}

data class MindMap(val id: Int, val title: String)