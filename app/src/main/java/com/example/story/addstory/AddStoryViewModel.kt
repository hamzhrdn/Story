package com.example.story.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.story.database.Repository
import com.example.story.network.response.PostStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val repository: Repository) : ViewModel() {
    fun postStory(file: MultipartBody.Part, description: RequestBody) =
        repository.postStory(file, description)
}