package com.example.story.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.story.network.ApiService
import com.example.story.network.StoryPaging
import com.example.story.network.response.LoginResponse
import com.example.story.network.response.PostStoryResponse
import com.example.story.network.response.RegisterResponse
import com.example.story.network.response.StoryItem
import com.example.story.utils.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
            Log.e("RegisterViewModel", "postRegister: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStory(): LiveData<PagingData<StoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPaging(apiService)
            }
        ).liveData
    }

    fun postStory(file: MultipartBody.Part, description: RequestBody):LiveData<Result<PostStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postStory(file, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("AddStoryViewModel", "postStory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
}