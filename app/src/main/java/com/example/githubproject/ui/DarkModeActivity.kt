package com.example.githubproject.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.githubproject.databinding.ActivityDarkModeBinding
import com.example.githubproject.viewmodel.DarkModeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DarkModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkModeBinding
    private val viewModel: DarkModeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = binding.switchTheme

        lifecycleScope.launch {
            viewModel.getThemeSetting().collect { isDarkModeActive ->
                switchTheme.isChecked = isDarkModeActive
                updateTheme(isDarkModeActive)
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
            updateTheme(isChecked)
        }
    }

    private fun updateTheme(isDarkModeActive: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        setResult(Activity.RESULT_OK)
    }
}
