package com.tayari365.class10sciencenotes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityNotesLoaderBinding

class PhysicsNotesLoaderActivity : BaseActivity(), NotesLoaderAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityNotesLoaderBinding
    private lateinit var adapter: NotesLoaderAdapter
    private var isFromLectures: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityNotesLoaderBinding.inflate(layoutInflater)
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

        isFromLectures = intent.getBooleanExtra("FROM_LECTURES", false)

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.toolbarTitle.text = if (isFromLectures) "Physics Lectures" else "Physics Notes"
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        val items = if (isFromLectures) loadPhysicsLectures() else loadPhysicsNotes()
        adapter = NotesLoaderAdapter(items, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadPhysicsNotes(): List<NotesLoaderItem> = listOf(
        NotesLoaderItem(1, "Scientific Study"),
        NotesLoaderItem(2, "Force and Motion"),
        NotesLoaderItem(3, "Pressure"),
        NotesLoaderItem(4, "Heat"),
        NotesLoaderItem(5, "Wave"),
        NotesLoaderItem(6, "Electricity and Magnetism"),
        NotesLoaderItem(7, "The Universe"),
        NotesLoaderItem(8, "ICT")
    )

    private fun loadPhysicsLectures(): List<NotesLoaderItem> = listOf(
        NotesLoaderItem(1, "Scientific Study", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI6aZbW15DQ5aHI-dPJDzaCN"),
        NotesLoaderItem(2, "Force and Motion", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI4pQnUU55WKLBU5H7UbKpyP"),
        NotesLoaderItem(3, "Pressure", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI7GtJRxINpB_ZgJ05IG_YQI"),
        NotesLoaderItem(4, "Heat", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI6QyDj6EOkBSt27rrk8tqZc"),
        NotesLoaderItem(5, "Wave", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI6S0DvZD9xSAYDcrUJt5AR-"),
        NotesLoaderItem(6, "Electricity and Magnetism", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI6sXzN_3jmnwtyZQ2E1CMtA"),
        NotesLoaderItem(7, "The Universe", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI7gzVbL4j23WA6Y9F6Jz8bs"),
        NotesLoaderItem(8, "ICT", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI58cPMjdg8GoO2sB7nG2AcZ")
    )

    override fun onNoteClicked(position: Int) {
        if (isFromLectures) {
            val videoUrl = adapter.getItems()[position].videoUrl
            if (videoUrl != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                startActivity(intent)
            } else {
                Toast.makeText(this, "Video URL not available", Toast.LENGTH_SHORT).show()
            }
        } else {
            val intent = Intent(this, NotesViewerActivity::class.java).apply {
                putExtra("SUBJECT_ID", "physics")
                putExtra("POSITION", position)
            }
            startActivity(intent)
        }
    }
}