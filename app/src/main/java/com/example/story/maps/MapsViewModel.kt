package com.example.story.maps

import androidx.lifecycle.ViewModel
import com.example.story.database.Repository

class MapsViewModel(private val repository: Repository) : ViewModel() {
    fun getStoriesWithLocation() = repository.getStoryWithLocation()
}