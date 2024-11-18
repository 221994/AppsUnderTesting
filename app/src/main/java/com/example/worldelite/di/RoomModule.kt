package com.example.worldelite.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.worldelite.room.dao.PostDao
import com.example.worldelite.room.database.PostDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PostDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            PostDataBase::class.java,
            "my_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePostDao(postDataBase: PostDataBase): PostDao {
        return postDataBase.postDao()
    }






}