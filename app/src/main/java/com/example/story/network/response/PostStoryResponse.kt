package com.example.story.network.response

import com.google.gson.annotations.SerializedName

data class PostStoryResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
