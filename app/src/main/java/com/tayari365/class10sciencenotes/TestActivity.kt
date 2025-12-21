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
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class TestActivity : BaseActivity() {
    private lateinit var toolbarTitle: TextView
    private lateinit var pdfView: PDFView
    private lateinit var currentTopicName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        // Initialize Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Disable the default title in action bar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Set a custom size for the navigation icon (Close Icon)
        val icon = resources.getDrawable(R.drawable.ic_close, null)
        val iconWidth = 35
        val iconHeight = 35

        // Convert dp to pixels
        val scale = resources.displayMetrics.density
        val iconWidthPx = (iconWidth * scale).toInt()
        val iconHeightPx = (iconHeight * scale).toInt()

        // Set custom bounds for the icon
        icon.setBounds(0, 0, iconWidthPx, iconHeightPx)

        // Set the custom icon to the toolbar
        toolbar.navigationIcon = icon

        // Set the navigation icon listener to close the activity
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Initialize custom TextView for toolbar title
        toolbarTitle = findViewById(R.id.toolbar_title)

        // Initialize PDF Viewer
        pdfView = findViewById(R.id.pdfView)

        // Get topic ID, position, and title from Intent
        val topicId = intent.getIntExtra("TOPIC_ID", -1)
        val position = intent.getIntExtra("POSITION", -1)
        currentTopicName = intent.getStringExtra("TOPIC_TITLE") ?: "Unknown Topic"

        // Set the toolbar title dynamically
        setCustomToolbarTitle(currentTopicName)

        // Load the PDF
        loadPdf(position)
    }

    private fun loadPdf(position: Int) {
        val pdfFileName = "Test ${position + 1}.pdf"
        try {
            pdfView.fromAsset(pdfFileName)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(DefaultScrollHandle(this))
                .load()
        } catch (e: IOException) {
            Toast.makeText(this, "Failed to load PDF: $pdfFileName", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            finish()
        }
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
        return FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
    }

    private fun shareContent(imageUri: Uri) {
        val shareText = "Check out this content from $currentTopicName in MeroNotes Class 10 Science Notes app!"
        val appLink = "https://play.google.com/store/apps/details?id=${packageName}"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT, "$shareText\n\nDownload the app: $appLink")
            putExtra(Intent.EXTRA_SUBJECT, "MeroNotes Class 10 Science Notes - $currentTopicName")

            val imageUris = ArrayList<Uri>()
            imageUris.add(imageUri)
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)

            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun setCustomToolbarTitle(title: String) {
        toolbarTitle.text = title
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

