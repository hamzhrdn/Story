package com.example.story.utils

import android.content.Context
import com.example.story.database.Repository
import com.example.story.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        return Repository(apiService)
    }
}