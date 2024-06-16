package com.example.story

import com.example.story.network.response.StoryItem
import com.example.story.network.response.StoryResponse

object DataDummy {
    fun generateDummyStoryResponse(): List<StoryItem> {
        val items: MutableList<StoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = StoryItem(
                i.toString(),
                "datetime",
                "alpha",
                "ini story",
                0.0,
                "$i",
                0.0
            )
            items.add(quote)
        }
        return items
    }
}