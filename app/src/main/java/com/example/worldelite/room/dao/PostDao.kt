package com.example.worldelite.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.worldelite.model.data.Post
import androidx.room.Query

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(posts:List<Post>)

    @Query(" SELECT * FROM table_posts where id = :id ")
    suspend fun getPostUserById(id: Int): List<Post>

    @Query("Delete from table_posts")
    suspend fun deleteAllPosts()
    @Query(" SELECT * from table_posts")
    suspend fun getALlPosts():List<Post>
//    @Query("SELECT * FROM table_posts WHERE userId = :userId")
//    suspend fun getPostsByUserId(userId: Int): List<Post>
}