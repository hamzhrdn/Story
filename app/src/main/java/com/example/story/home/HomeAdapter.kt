package com.example.story.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.story.databinding.ListStoryBinding
import com.example.story.network.response.StoryItem

class HomeAdapter(private val callback: (story: StoryItem, imageView: View, nameView: View, descView: View) -> Unit) : PagingDataAdapter<StoryItem, StoriesViewHolder>(DIFF_CALLBACK){

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.root.setOnClickListener{
            item?.let {
                callback.invoke(
                    item,
                    holder.view.image,
                    holder.view.storyTitle,
                    holder.view.storyDescription
                )
            }
        }
        item?.let {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(view)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

class StoriesViewHolder(val view: ListStoryBinding) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: StoryItem) {
        Glide.with(view.root.context).load(item.photoUrl).into(view.image)
        view.storyTitle.text = item.name.toString()
        view.storyDescription.text = item.description.toString()
    }
}