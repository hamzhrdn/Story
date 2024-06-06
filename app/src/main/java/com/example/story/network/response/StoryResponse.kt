package com.example.story.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey

data class StoryResponse(
	val listStory: List<StoryItem> = emptyList(),
	val error: Boolean? = null,
	val message: String? = null
)

data class StoryItem(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val lon: Double? = null,
	@PrimaryKey
	val id: String? = null,
	val lat: Double? = null
)

