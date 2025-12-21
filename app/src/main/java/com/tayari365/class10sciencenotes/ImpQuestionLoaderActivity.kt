package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import androidx.core.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityImpQuestionLoaderBinding

class ImpQuestionLoaderActivity : BaseActivity(), NotesLoaderAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityImpQuestionLoaderBinding
    private lateinit var adapter: NotesLoaderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityImpQuestionLoaderBinding.inflate(layoutInflater)
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
        binding.toolbarTitle.text = "Important Questions"
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        val items = loadImpQuestions()
        adapter = NotesLoaderAdapter(items, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadImpQuestions(): List<NotesLoaderItem> {
        return listOf(
            NotesLoaderItem(1, "Scientific Study"),
            NotesLoaderItem(2, "Classification of Living Beings"),
            NotesLoaderItem(3, "Life Cycle"),
            NotesLoaderItem(4, "Heredity"),
            NotesLoaderItem(5, "Physiological Structure and Life Process"),
            NotesLoaderItem(6, "Nature and Environment"),
            NotesLoaderItem(7, "Force and Motion"),
            NotesLoaderItem(8, "Pressure"),
            NotesLoaderItem(9, "Heat"),
            NotesLoaderItem(10, "Wave"),
            NotesLoaderItem(11, "Electricity and Magnetism"),
            NotesLoaderItem(12, "The Universe"),
            NotesLoaderItem(13, "ICT"),
            NotesLoaderItem(14, "Classification of Elements"),
            NotesLoaderItem(15, "Chemical Reactions"),
            NotesLoaderItem(16, "Gases"),
            NotesLoaderItem(17, "Metals"),
            NotesLoaderItem(18, "Hydrocarbons and Their Compounds"),
            NotesLoaderItem(19, "Materials Used in Daily Life")
        )
    }

    override fun onNoteClicked(position: Int) {
        val intent = Intent(this, NotesViewerActivity::class.java).apply {
            putExtra("SUBJECT_ID", "imp_question")
            putExtra("POSITION", position)
        }
        startActivity(intent)
    }
}