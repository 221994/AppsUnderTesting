package com.example.worldelite.repository

import android.util.Log
import com.example.worldelite.model.data.Post
import com.example.worldelite.network.api.PostsApiService
import com.example.worldelite.room.dao.PostDao
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val postsAPiService: PostsApiService, private val postDao: PostDao
) {
    suspend fun getAllPosts(): List<Post> {
        val localPosts = postDao.getALlPosts()
        return if (localPosts.isNotEmpty()) {
            try {
                val networkPosts = postsAPiService.getPosts()
                postDao.insertAllPosts(networkPosts)
                Log.d("TAG", "Fetched a data from api and sorted it into local database.")
                postDao.getALlPosts()
            } catch (e: Exception) {
                Log.d("TAG", "Error to fetch a new data so we fetched old data from local.")
                return localPosts
            }
        } else {
            try {
                val networkPosts = postsAPiService.getPosts()
                postDao.insertAllPosts(networkPosts)
                Log.d("TAG", "Local data was empty and sorted a new list.")
                networkPosts
            } catch (e: Exception) {
                Log.d("TAG", "Local data is empty and failed to fetch a new data. ")
                emptyList()
            }

        }
    }

    suspend fun getPostsByUser(userId: Int): List<Post> {
        return postDao.getPostUserById(userId)
    }
}