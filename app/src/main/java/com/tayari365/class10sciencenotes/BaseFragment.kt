package com.tayari365.class10sciencenotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tayari365.class10sciencenotes.utils.ThemeManager

open class BaseFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateTheme()
    }

    private fun updateTheme() {
        // You can add specific theme-related logic here if needed
    }

    fun isDarkMode(): Boolean {
        return ThemeManager.isDarkMode(requireContext())
    }
}