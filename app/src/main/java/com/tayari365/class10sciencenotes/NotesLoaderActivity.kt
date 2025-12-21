package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.tayari365.class10sciencenotes.databinding.ActivityNotesLoaderBinding
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class NotesLoaderActivity : BaseActivity(), NotesLoaderAdapter.OnNoteClickListener {

    private lateinit var binding: ActivityNotesLoaderBinding
    private lateinit var adapter: NotesLoaderAdapter
    private lateinit var subjectId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        binding = ActivityNotesLoaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        // Assuming your toolbar (colorPrimaryVariant) is dark, so use light status bar icons (false)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = false
        // Assuming your RecyclerView content is light, so use dark navigation bar icons (true)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightNavigationBars = true

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = systemBars.top,
                bottom = systemBars.bottom
            )
            insets
        }

        subjectId = intent.getStringExtra("SUBJECT_ID") ?: "physics"

        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        binding.toolbarTitle.text = when (subjectId) {
            "physics" -> "Physics Notes"
            "chemistry" -> "Chemistry Notes"
            "biology" -> "Biology Notes"
            else -> "Notes"
        }
        binding.backButton.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        val notes = loadNotes()
        adapter = NotesLoaderAdapter(notes, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun loadNotes(): List<NotesLoaderItem> {
        return when (subjectId) {
            "physics" -> listOf(
                NotesLoaderItem(1, "Kinematics"),
                NotesLoaderItem(2, "Dynamics"),
                NotesLoaderItem(3, "Work, Energy, and Power"),
                NotesLoaderItem(4, "Waves and Sound"),
                NotesLoaderItem(5, "Light and Optics")
            )
            "chemistry" -> listOf(
                NotesLoaderItem(1, "Atomic Structure"),
                NotesLoaderItem(2, "Chemical Bonding"),
                NotesLoaderItem(3, "Periodic Table"),
                NotesLoaderItem(4, "Chemical Reactions"),
                NotesLoaderItem(5, "Acids, Bases, and Salts")
            )
            "biology" -> listOf(
                NotesLoaderItem(1, "Cell Structure and Function"),
                NotesLoaderItem(2, "Genetics and Heredity"),
                NotesLoaderItem(3, "Human Physiology"),
                NotesLoaderItem(4, "Ecology and Environment"),
                NotesLoaderItem(5, "Evolution and Biodiversity")
            )
            else -> emptyList()
        }
    }

    override fun onNoteClicked(position: Int) {
        val intent = Intent(this, NotesViewerActivity::class.java).apply {
            putExtra("SUBJECT_ID", subjectId)
            putExtra("POSITION", position)
        }
        startActivity(intent)

        Toast.makeText(this, "Opening note ${position + 1}", Toast.LENGTH_SHORT).show()
    }
}