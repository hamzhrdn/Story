package com.example.story.network.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

data class StoryResponse(
	val listStory: List<StoryItem> = emptyList(),
	val error: Boolean? = null,
	val message: String? = null
)

@Entity(tableName = "story")
data class StoryItem(
	val photoUrl: String,
	val createdAt: String,
	val name: String,
	val description: String,
	val lon: Double,
	@PrimaryKey
	val id: String,
	val lat: Double
)

