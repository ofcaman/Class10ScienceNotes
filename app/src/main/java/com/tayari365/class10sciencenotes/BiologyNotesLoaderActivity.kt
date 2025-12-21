package com.tayari365.class10sciencenotes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityNotesLoaderBinding

class BiologyNotesLoaderActivity : BaseActivity(), NotesLoaderAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityNotesLoaderBinding
    private lateinit var adapter: NotesLoaderAdapter
    private var isFromLectures = false

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
        binding.toolbarTitle.text = if (isFromLectures) "Biology Lectures" else "Biology Notes"
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        val items = if (isFromLectures) loadBiologyLectures() else loadBiologyNotes()
        adapter = NotesLoaderAdapter(items, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadBiologyNotes(): List<NotesLoaderItem> = listOf(
        NotesLoaderItem(1, "Classification of Living Beings"),
        NotesLoaderItem(2, "Life Cycle"),
        NotesLoaderItem(3, "Heredity"),
        NotesLoaderItem(4, "Physiological Structure and Life Process"),
        NotesLoaderItem(5, "Nature and Environment")
    )

    private fun loadBiologyLectures(): List<NotesLoaderItem> = listOf(
        NotesLoaderItem(1, "Classification of Living Beings", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI5FECe6kxipKE-ESLkHqUqM"),
        NotesLoaderItem(2, "Life Cycle", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI40vqlJW9Q0xAAAbh8JJY3n"),
        NotesLoaderItem(3, "Heredity", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI7-V5uFtODqZoHj93xYi_ET"),
        NotesLoaderItem(4, "Physiological Structure and Life Process", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI4O56vm3RUBKfA7_t1F5OKP"),
        NotesLoaderItem(5, "Nature and Environment", "https://www.youtube.com/watch?v=nhaapiOdWH8&list=PLiHK2-qlGhI7JXciqxnR_Pb9b_PVWFtc8")
    )

    override fun onNoteClicked(position: Int) {
        if (isFromLectures) {
            val videoUrl = adapter.getItems()[position].videoUrl
            if (videoUrl != null) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
            } else {
                Toast.makeText(this, "Video URL not available", Toast.LENGTH_SHORT).show()
            }
        } else {
            val intent = Intent(this, NotesViewerActivity::class.java).apply {
                putExtra("SUBJECT_ID", "biology")
                putExtra("POSITION", position)
            }
            startActivity(intent)
        }
    }
}