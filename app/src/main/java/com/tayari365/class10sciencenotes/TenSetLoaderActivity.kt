package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityTenSetLoaderBinding

class TenSetLoaderActivity : BaseActivity(), NotesLoaderAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityTenSetLoaderBinding
    private lateinit var adapter: NotesLoaderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityTenSetLoaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set status bar color
        window.statusBarColor = getColor(R.color.colorPrimary)

        // Set white icons (for dark status bar)
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.isAppearanceLightStatusBars = false

        // Adjust RecyclerView padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            insets
        }

        // Adjust toolbar height + top padding for Android 15+
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val defaultToolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height_with_status)
            view.layoutParams.height = statusBarHeight + defaultToolbarHeight
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.toolbarTitle.text = "Ten Sets"
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        val items = loadTenSets()
        adapter = NotesLoaderAdapter(items, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadTenSets(): List<NotesLoaderItem> {
        return listOf(
            NotesLoaderItem(1, "Set One"),
            NotesLoaderItem(2, "Set Two"),
            NotesLoaderItem(3, "Set Three"),
            NotesLoaderItem(4, "Set Four")
        )
    }

    override fun onNoteClicked(position: Int) {
        val intent = Intent(this, NotesViewerActivity::class.java).apply {
            putExtra("SUBJECT_ID", "ten_set")
            putExtra("POSITION", position)
        }
        startActivity(intent)
    }
}
