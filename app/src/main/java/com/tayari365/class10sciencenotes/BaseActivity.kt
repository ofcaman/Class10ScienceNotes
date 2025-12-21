package com.tayari365.class10sciencenotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tayari365.class10sciencenotes.utils.ThemeManager

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applyTheme(this)
        super.onCreate(savedInstanceState)
    }
}