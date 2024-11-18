package com.example.worldelite.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.worldelite.model.data.Post
import com.example.worldelite.room.dao.PostDao

@Database(entities = [Post::class], version = 2)
abstract class PostDataBase  : RoomDatabase() {
    abstract fun postDao(): PostDao
}