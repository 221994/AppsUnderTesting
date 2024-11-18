package com.example.worldelite.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_posts")
data class Post( val userId: Int, @PrimaryKey val id: Int, val title: String, val body: String)
