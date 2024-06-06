package com.example.story.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.story.network.ApiService
import com.example.story.network.StoryPaging
import com.example.story.network.response.LoginResponse
import com.example.story.network.response.RegisterResponse
import com.example.story.network.response.StoryItem
import com.example.story.utils.Result
import kotlinx.coroutines.flow.Flow

class Repository(private val apiService: ApiService){
    fun postLogin(email: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, password)
            emit(Result.Success(response))
        }catch (e: Exception){
            Log.e("LoginViewModel", "postLogin: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postRegister(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData{
        emit(Result.Loading)
        try {
            val response = apiService.postRegister(name, email, password)
            emit(Result.Success(response))
        }catch (e: Exception){
            Log.e("LoginViewModel", "postRegister: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStory(): Flow<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPaging(apiService)
            }
        ).flow
    }
}