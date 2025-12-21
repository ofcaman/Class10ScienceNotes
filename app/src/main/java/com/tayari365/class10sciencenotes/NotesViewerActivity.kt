package com.tayari365.class10sciencenotes

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.*
import com.tayari365.class10sciencenotes.databinding.ActivityNotesViewerBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class NotesViewerActivity : BaseActivity() {

    private lateinit var binding: ActivityNotesViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityNotesViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set light icons (white) for dark status bar
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController?.isAppearanceLightStatusBars = false

        // Set status bar color to colorPrimary with fallback
        try {
            val color = getColor(R.color.colorPrimary)
            window.statusBarColor = color
            Log.d("NotesViewerActivity", "Status bar color set to: $color")
        } catch (e: Exception) {
            Log.e("NotesViewerActivity", "Failed to set status bar color: ${e.message}")
            window.statusBarColor = android.graphics.Color.BLACK // Fallback color
        }

        // Adjust toolbar height + top padding for Android 15+
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val defaultToolbarHeight = resources.getDimensionPixelSize(R.dimen.toolbar_height_with_status)
            view.layoutParams.height = statusBarHeight + defaultToolbarHeight
            view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, view.paddingBottom)
            insets
        }

        // Adjust PDFView padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.pdfView) { view, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemInsets.bottom)
            insets
        }

        // Get all the passed intent data
        val subjectId = intent.getStringExtra("SUBJECT_ID") ?: ""
        val position = intent.getIntExtra("POSITION", -1)
        val isModelSet = intent.getBooleanExtra("IS_MODEL_SET", false)
        val isPyp = intent.getBooleanExtra("IS_PYP", false)
        val isTenSet = subjectId == "ten_set"
        val isImpQuestion = subjectId == "imp_question"
        val isFormulas = subjectId == "formulas"
        val isMindMap = subjectId == "mind_map"
        val mindMapName = intent.getStringExtra("MIND_MAP_NAME")

        if (subjectId.isEmpty() && !isFormulas && !isMindMap) {
            Toast.makeText(this, "Invalid subject or position", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar(subjectId, position, isModelSet, isPyp, isTenSet, isImpQuestion, isFormulas, isMindMap, mindMapName)
        loadPdf(subjectId, position, isModelSet, isPyp, isTenSet, isImpQuestion, isFormulas, isMindMap)
    }

    private fun setupToolbar(
        subjectId: String, position: Int,
        isModelSet: Boolean, isPyp: Boolean, isTenSet: Boolean,
        isImpQuestion: Boolean, isFormulas: Boolean,
        isMindMap: Boolean, mindMapName: String?
    ) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.toolbarTitle.text = when {
            isModelSet -> "Model Set ${position + 1}"
            isPyp -> "Past Year Paper ${position + 1}"
            isTenSet -> "Ten Set ${position + 1}"
            isImpQuestion -> "Important Question Set ${position + 1}"
            isFormulas -> "Formulas"
            isMindMap -> mindMapName ?: "Mind Map ${position + 1}"
            subjectId == "physics" -> "Physics Note ${position + 1}"
            subjectId == "chemistry" -> "Chemistry Note ${position + 1}"
            subjectId == "biology" -> "Biology Note ${position + 1}"
            else -> "Note ${position + 1}"
        }

        binding.backButton.setOnClickListener { finish() }
    }

    private fun loadPdf(
        subjectId: String, position: Int,
        isModelSet: Boolean, isPyp: Boolean, isTenSet: Boolean,
        isImpQuestion: Boolean, isFormulas: Boolean, isMindMap: Boolean
    ) {
        val pdfFileName = getPdfFileName(subjectId, position, isModelSet, isPyp, isTenSet, isImpQuestion, isFormulas, isMindMap)

        if (pdfFileName.isNotEmpty()) {
            try {
                binding.pdfView.fromAsset(pdfFileName)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .onError { t ->
                        Toast.makeText(this, "Error loading PDF: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                    .load()
            } catch (e: Exception) {
                Toast.makeText(this, "Error loading PDF: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Invalid PDF file name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPdfFileName(
        subjectId: String, position: Int,
        isModelSet: Boolean, isPyp: Boolean, isTenSet: Boolean,
        isImpQuestion: Boolean, isFormulas: Boolean, isMindMap: Boolean
    ): String {
        return when {
            isModelSet -> "set_${position + 1}.pdf"
            isPyp -> "pyp_${position + 1}.pdf"
            isTenSet -> "tenset_${position + 1}.pdf"
            isImpQuestion -> "imp_que_${position + 1}.pdf"
            isFormulas -> "formulas.pdf"
            isMindMap -> "mind_${position + 1}.pdf"
            subjectId == "physics" -> when (position) {
                0 -> "physics_note_1.pdf"
                1 -> "physics_note_2.pdf"
                2 -> "physics_note_3.pdf"
                3 -> "physics_note_4.pdf"
                4 -> "physics_note_5.pdf"
                5 -> "physics_note_6.pdf"
                6 -> "physics_note_7.pdf"
                7 -> "physics_note_8.pdf"
                else -> ""
            }
            subjectId == "chemistry" -> when (position) {
                0 -> "chem_1.pdf"
                1 -> "chem_2.pdf"
                2 -> "chem_3.pdf"
                3 -> "chem_4.pdf"
                4 -> "chem_5.pdf"
                5 -> "chem_6.pdf"
                else -> ""
            }
            subjectId == "biology" -> when (position) {
                0 -> "bio_1.pdf"
                1 -> "bio_2.pdf"
                2 -> "bio_3.pdf"
                3 -> "bio_4.pdf"
                4 -> "bio_5.pdf"
                else -> ""
            }
            else -> ""
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
        val shareText = "Check out this content from ${binding.toolbarTitle.text} in Tayari365 Class 10 Science Notes app!"
        val appLink = "https://play.google.com/store/apps/details?id=${packageName}"

        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            putExtra(Intent.EXTRA_TEXT, "$shareText\n\nDownload the app: $appLink")
            putExtra(Intent.EXTRA_SUBJECT, "Tayari365 Class 10 Science Notes - ${binding.toolbarTitle.text}")
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayListOf(imageUri))
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