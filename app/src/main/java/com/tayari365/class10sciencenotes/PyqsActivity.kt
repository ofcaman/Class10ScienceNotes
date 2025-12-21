package com.tayari365.class10sciencenotes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import kotlin.random.Random
import androidx.core.view.WindowCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import android.widget.TextView // Import TextView for toolbar title

class PyqsActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PyqsAdapter

    private val colors = listOf(
        Color.parseColor("#FF0000"), // Red
        Color.parseColor("#FFD700"), // Yellow
        Color.parseColor("#0000FF"), // Blue
        Color.parseColor("#4B0082"), // Indigo
        Color.parseColor("#8A2BE2"), // Violet
        Color.parseColor("#87CEEB")  // Sky Blue
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pyqs)

        // Set status bar and navigation bar to transparent
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // Set status bar icon color based on your toolbar's background (assuming dark toolbar, use light icons)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = false
        // Set navigation bar icon color based on your RecyclerView's background (assuming light content, use dark icons)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightNavigationBars = true

        // Apply system window insets as padding to the root view
        val rootView: View = findViewById(android.R.id.content) // Get the root view of the activity
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = systemInsets.top,
                bottom = systemInsets.bottom
            )
            insets
        }

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set custom toolbar title
        val toolbarTitle: TextView = findViewById(R.id.toolbarTitle) // Assuming you have a TextView with this ID in your toolbar layout
        toolbarTitle.text = "Model Questions"

        // Setup back button
        findViewById<View>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create PYQ list with random colors
        val pyqsList = (1..4).map { number ->
            PyqItem(
                number = number,
                name = "Model Set $number",
                backgroundColor = getRandomColor()
            )
        }

        // Set up adapter
        adapter = PyqsAdapter(pyqsList)
        recyclerView.adapter = adapter
    }

    private fun getRandomColor(): Int {
        return colors[Random.nextInt(colors.size)]
    }
}