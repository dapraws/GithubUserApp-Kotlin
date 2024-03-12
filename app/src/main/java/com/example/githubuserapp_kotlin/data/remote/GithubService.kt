package com.example.githubuserapp_kotlin.data.remote

import com.example.githubuserapp_kotlin.data.model.ResponseDetailUser
import com.example.githubuserapp_kotlin.data.model.ResponseUserGithub
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserGithub(): MutableList<ResponseUserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUserGithub(@Path("username") username: String): ResponseDetailUser

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowersUserGithub(@Path("username") username: String): MutableList<ResponseUserGithub.Item>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowingUserGithub(@Path("username") username: String): MutableList<ResponseUserGithub.Item>
}