package com.tayari365.class10sciencenotes

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.*
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import androidx.core.content.FileProvider

class ChapterLoadActivity : BaseActivity() {
    private lateinit var toolbarTitle: TextView
    private lateinit var pdfView: PDFView
    private lateinit var currentChapterName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_load)

        // Set status bar color to colorPrimary
        window.statusBarColor = getColor(R.color.colorPrimary)

        // Set light icons (white) for dark status bar
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.isAppearanceLightStatusBars = false

        // Adjust toolbar height + top padding for Android 15+
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val defaultToolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height_with_status)
            view.layoutParams.height = statusBarHeight + defaultToolbarHeight
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        // Adjust PDFView padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<PDFView>(R.id.pdfView)) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            insets
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val icon = resources.getDrawable(R.drawable.ic_close, null)
        val iconWidthDp = 35
        val iconHeightDp = 35
        val scale = resources.displayMetrics.density
        icon.setBounds(0, 0, (iconWidthDp * scale).toInt(), (iconHeightDp * scale).toInt())
        toolbar.navigationIcon = icon
        toolbar.setNavigationOnClickListener { finish() }

        toolbarTitle = findViewById(R.id.toolbarTitle)
        pdfView = findViewById(R.id.pdfView)

        val chapterPosition = intent.getIntExtra("CHAPTER_POSITION", 0)
        currentChapterName = intent.getStringExtra("CHAPTER_NAME") ?: "Chapter ${chapterPosition + 1}"

        setCustomToolbarTitle(currentChapterName)
        loadPdf(chapterPosition)
    }

    private fun loadPdf(chapterPosition: Int) {
        val pdfFileName = when (chapterPosition) {
            0 -> "physics_note_1.pdf"
            1 -> "bio_1.pdf"
            2 -> "bio_2.pdf"
            3 -> "bio_3.pdf"
            4 -> "bio_4.pdf"
            5 -> "bio_5.pdf"
            6 -> "physics_note_2.pdf"
            7 -> "physics_note_3.pdf"
            8 -> "physics_note_4.pdf"
            9 -> "physics_note_5.pdf"
            10 -> "physics_note_6.pdf"
            11 -> "physics_note_7.pdf"
            12 -> "physics_note_8.pdf"
            13 -> "chem_1.pdf"
            14 -> "chem_4.pdf"
            15 -> "chem_2.pdf"
            16 -> "chem_3.pdf"
            17 -> "chem_5.pdf"
            18 -> "chem_6.pdf"
            else -> null
        }

        if (pdfFileName != null) {
            pdfView.fromAsset(pdfFileName)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(DefaultScrollHandle(this))
                .onError { t ->
                    Toast.makeText(this, "Error loading PDF: ${t.message}", Toast.LENGTH_LONG).show()
                }
                .load()
        } else {
            Toast.makeText(this, "Invalid chapter selected", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setCustomToolbarTitle(title: String) {
        toolbarTitle.text = title
    }

    private fun captureScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    @Throws(IOException::class)
    private fun saveScreenshotToFile(bitmap: Bitmap): Uri {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
    }

    private fun shareContent(imageUri: Uri) {
        val shareText = "Check out this content from $currentChapterName in MeroNotes Class 10 Science Notes app!"
        val appLink = "https://play.google.com/store/apps/details?id=$packageName"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT, "$shareText\n\nDownload the app: $appLink")
            putExtra(Intent.EXTRA_SUBJECT, "MeroNotes Class 10 Science Notes - $currentChapterName")

            val imageUris = ArrayList<Uri>()
            imageUris.add(imageUri)
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)

            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.custom_menu1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_screenshot -> {
                try {
                    val rootView = window.decorView.rootView
                    val screenshot = captureScreenshot(rootView)
                    val imageUri = saveScreenshotToFile(screenshot)
                    shareContent(imageUri)
                } catch (e: IOException) {
                    Toast.makeText(this, "Failed to capture screenshot", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}