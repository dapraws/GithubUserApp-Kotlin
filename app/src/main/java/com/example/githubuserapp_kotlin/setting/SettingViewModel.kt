package com.example.githubuserapp_kotlin.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp_kotlin.data.local.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val preferences: SettingPreferences) : ViewModel() {

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDark)
        }
    }

    class Factory(private val preferences: SettingPreferences) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SettingViewModel(preferences) as T
    }
}