package com.example.githubuserapp_kotlin.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp_kotlin.data.remote.ApiClient
import com.example.githubuserapp_kotlin.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowersUser = MutableLiveData<Result>()
    val resultFollowingUser = MutableLiveData<Result>()

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubService
                        .getDetailUserGithub(username)
                    emit(response)
                }.onStart {
                    resultDetailUser.value = Result.Loading(true)
                }.onCompletion {
                    resultDetailUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultDetailUser.value = Result.Error(it)
                }.collect {
                    resultDetailUser.value = Result.Success(it)
                }
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubService
                        .getFollowersUserGithub(username)
                    emit(response)
                }.onStart {
                    resultFollowersUser.value = Result.Loading(true)
                }.onCompletion {
                    resultFollowersUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultFollowersUser.value = Result.Error(it)
                }.collect {
                    resultFollowersUser.value = Result.Success(it)
                }
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                flow {
                    val response = ApiClient
                        .githubService
                        .getFollowingUserGithub(username)
                    emit(response)
                }.onStart {
                    resultFollowingUser.value = Result.Loading(true)
                }.onCompletion {
                    resultFollowingUser.value = Result.Loading(false)
                }.catch {
                    Log.e("Error", it.message.toString())
                    it.printStackTrace()
                    resultFollowingUser.value = Result.Error(it)
                }.collect {
                    resultFollowingUser.value = Result.Success(it)
                }
            }
        }
    }
}