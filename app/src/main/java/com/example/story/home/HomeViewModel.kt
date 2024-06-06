package com.example.story.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.story.database.Repository
import com.example.story.network.response.StoryItem
import kotlinx.coroutines.flow.Flow

class HomeViewModel(repository: Repository) : ViewModel() {
    val stories: LiveData<PagingData<StoryItem>> = repository.getStory().cachedIn(viewModelScope)
}