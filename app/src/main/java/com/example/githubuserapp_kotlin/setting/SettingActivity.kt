package com.example.githubuserapp_kotlin.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.githubuserapp_kotlin.R
import com.example.githubuserapp_kotlin.data.local.SettingPreferences
import com.example.githubuserapp_kotlin.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getTheme().observe(this) {
            if (it) {
                binding.switchTheme.text = "Dark Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.switchTheme.text = "Light Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}