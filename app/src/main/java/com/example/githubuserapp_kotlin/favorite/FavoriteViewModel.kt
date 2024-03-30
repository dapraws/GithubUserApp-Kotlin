package com.example.githubuserapp_kotlin.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp_kotlin.data.local.DbModule
import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub

class FavoriteViewModel(private val dbModule: DbModule) : ViewModel() {

    fun getUserFavorite() = dbModule.userDao.loadAll()

    fun deleteFavorite(user: ResponseUserGithub.Item) {
        dbModule.userDao.delete(user)
    }

    class Factory(private val db: DbModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}