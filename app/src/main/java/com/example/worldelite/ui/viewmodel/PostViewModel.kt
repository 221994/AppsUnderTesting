package com.example.worldelite.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.worldelite.model.data.Post
import com.example.worldelite.repository.PostsRepository
import com.example.worldelite.util.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postsRepository: PostsRepository) :
    ViewModel() {

    private val _posts = MutableLiveData<ApiStatus<List<Post>>>()
    val liveData: LiveData<ApiStatus<List<Post>>> get() = _posts
    private val _userSearchStatus: MutableLiveData<String?> = MutableLiveData<String?>()
    val userSearchStatus: LiveData<String?> get() = _userSearchStatus


    fun fetchPosts() {
        _posts.value = ApiStatus.Loading
        viewModelScope.launch {
            try {
                val posts = postsRepository.getAllPosts()
                _posts.value = ApiStatus.Success(posts)
            } catch (e: Exception) {
                _posts.value =
                    ApiStatus.Error("Could not fetch posts; please check your connection.")
            }
        }
    }

    fun fetchPostsByUserId(userId: Int) {
        _posts.value = ApiStatus.Loading
        viewModelScope.launch {
            try {
                val userPosts = postsRepository.getPostsByUser(userId)
                if (userPosts.isEmpty()) {
                    _userSearchStatus.value = "User $userId not found"
                    _posts.value = ApiStatus.Error("User id $userId not found.")
                    Log.d("TAG", " user id is $userId")
                } else {
                    _userSearchStatus.value = null
                    _posts.value=ApiStatus.Success(userPosts)
                }
            } catch (e: Exception) {
                _posts.value = ApiStatus.Error("Error fetching posts by userId")
            }
        }
    }


}


