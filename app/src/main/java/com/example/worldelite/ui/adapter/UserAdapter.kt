package com.example.worldelite.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.worldelite.databinding.PostsCustomLayoutBinding
import com.example.worldelite.model.data.Post

class UserAdapter : ListAdapter<Post, UserAdapter.UserViewHolder>(PostsDifUtil) {

    class UserViewHolder(private val binding: PostsCustomLayoutBinding) : ViewHolder(binding.root) {
        fun bindViews(post: Post) {
            binding.tvId.text = post.id.toString()
            binding.tvUserBody.text = post.body
            binding.tvUserTitle.text = post.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = PostsCustomLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

}

object PostsDifUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}