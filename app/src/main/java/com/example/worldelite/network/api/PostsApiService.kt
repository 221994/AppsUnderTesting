package com.example.worldelite.network.api

import com.example.worldelite.model.data.Post
import retrofit2.http.GET

interface PostsApiService {

    @GET("/posts")
    suspend fun getPosts(): List<Post>

}