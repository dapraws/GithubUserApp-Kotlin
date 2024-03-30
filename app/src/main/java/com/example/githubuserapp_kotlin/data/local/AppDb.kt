package com.example.githubuserapp_kotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub

@Database(entities = [ResponseUserGithub.Item::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}