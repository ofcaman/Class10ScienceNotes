package com.tayari365.class10sciencenotes

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.tayari365.class10sciencenotes.databinding.ActivityMainBinding
import com.tayari365.class10sciencenotes.utils.ThemeManager
import androidx.core.content.ContextCompat

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarTitle: TextView
    private var currentFragment: Fragment? = null
    private val fragmentMap = mutableMapOf<Class<out Fragment>, Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout, allowing content to draw behind system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Status Bar Color to colorPrimary from your app's theme
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        // Set status bar icon color based on your toolbar's background (assuming colorPrimary is dark, use light icons)
        WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = false

        // Apply top system window insets as padding to the toolbar
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = systemBars.top,
                left = systemBars.left,
                right = systemBars.right
            )
            // Consume these insets for the toolbar
            WindowInsetsCompat.CONSUMED
        }

        // Apply left/right system window insets to the main content frame
        // The bottom inset for the frame is handled by its constraint to the bottomNavigationView
        ViewCompat.setOnApplyWindowInsetsListener(binding.frame) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBars.left,
                right = systemBars.right
            )
            insets // Don't consume here, let BottomNavigationView handle bottom if needed
        }

        // *** FIX FOR BOTTOM NAVIGATION BAR ISSUE ***
        // Apply bottom system window insets as padding to the BottomNavigationView
        // This pushes its content (icons/text) up, making them visible,
        // while the BottomNavigationView's background can extend to the screen edge.
        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNavigationView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                bottom = systemBars.bottom,
                left = systemBars.left, // Also apply horizontal insets for consistency
                right = systemBars.right
            )
            // Consume these insets so that no other view tries to handle them at the bottom.
            WindowInsetsCompat.CONSUMED
        }

        setupToolbar()
        setupBottomNavigation()

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbarTitle = binding.toolbarTitle
        setCustomToolbarTitle("Class 10 Science Notes")
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.notes -> replaceFragment(NotesFragment())
                R.id.mocktest -> replaceFragment(MockTestFragment())
                R.id.lectures -> replaceFragment(LecturesFragment())
                R.id.about -> replaceFragment(AboutFragment())
                else -> false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        val tag = fragment.javaClass.simpleName
        var cachedFragment = fragmentMap[fragment::class.java]

        if (cachedFragment == null) {
            cachedFragment = fragment
            fragmentMap[fragment::class.java] = cachedFragment
        }

        // Hide current fragment if exists to avoid visual glitches during transition
        currentFragment?.let { transaction.hide(it) }

        if (cachedFragment.isAdded) {
            transaction.show(cachedFragment)
        } else {
            transaction.add(R.id.frame, cachedFragment, tag)
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commitAllowingStateLoss()

        currentFragment = cachedFragment
    }

    private fun setCustomToolbarTitle(title: String) {
        toolbarTitle.text = title
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.DayNight -> {
//                toggleDayNightMode()
//                true
//            }
            R.id.Playstore -> {
                openPlayStore()
                true
            }
            R.id.Share -> {
                shareApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleDayNightMode() {
        ThemeManager.toggleTheme(this)
        recreate()
    }

    private fun openPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = android.net.Uri.parse("https://play.google.com/store/apps/developer?id=Indiv%20Solutions")
            setPackage("com.android.vending")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://play.google.com/store/apps")))
        }
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Check out this app: Class 10 Science Notes")
            putExtra(Intent.EXTRA_TEXT, "I'm using Class 10 Science Notes app. Try it out: https://play.google.com/store/apps/details?id=$packageName")
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }
}