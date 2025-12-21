package com.tayari365.class10sciencenotes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityNotesLoaderBinding

class ChemistryNotesLoaderActivity : BaseActivity(), NotesLoaderAdapter.OnNoteClickListener {

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
        binding.toolbarTitle.text = if (isFromLectures) "Chemistry Lectures" else "Chemistry Notes"
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        val items = if (isFromLectures) loadChemistryLectures() else loadChemistryNotes()
        adapter = NotesLoaderAdapter(items, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadChemistryNotes(): List<NotesLoaderItem> = listOf(
        NotesLoaderItem(1, "Classification of Elements"),
        NotesLoaderItem(2, "Gases"),
        NotesLoaderItem(3, "Metals"),
        NotesLoaderItem(4, "Chemical Reactions"),
        NotesLoaderItem(5, "Hydrocarbons and Their Compounds"),
        NotesLoaderItem(6, "Materials Used in Daily Life")
    )

    private fun loadChemistryLectures(): List<NotesLoaderItem> = listOf(
        NotesLoaderItem(1, "Classification of Elements", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI5rJthG4QWO-AzXelCLatEb"),
        NotesLoaderItem(2, "Gases", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI4fjnIuToeZZ2_CtvanUOIa"),
        NotesLoaderItem(3, "Metals", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI5pK_aKZPdzl1fMokBhkBtP"),
        NotesLoaderItem(4, "Chemical Reactions", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI6sA9aADfVJIAR2nHe07C9M"),
        NotesLoaderItem(5, "Hydrocarbons and Their Compounds", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI6RgbWXdutdH8xY52gQZofi"),
        NotesLoaderItem(6, "Materials Used in Daily Life", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI5Msp4PfMyImryg0AI7ulO4")
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
                putExtra("SUBJECT_ID", "chemistry")
                putExtra("POSITION", position)
            }
            startActivity(intent)
        }
    }
}