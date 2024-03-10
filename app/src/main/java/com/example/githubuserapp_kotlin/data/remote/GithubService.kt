package com.example.githubuserapp_kotlin.data.remote

import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub
import retrofit2.http.GET

interface GithubService{

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub() : MutableList<ResponseUserGithub.Item>
}