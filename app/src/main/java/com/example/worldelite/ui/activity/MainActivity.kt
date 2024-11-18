package com.example.worldelite.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.worldelite.databinding.ActivityMainBinding
import com.example.worldelite.model.data.Post
import com.example.worldelite.ui.adapter.UserAdapter
import com.example.worldelite.ui.viewmodel.PostViewModel
import com.example.worldelite.util.ApiStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val postsViewModel: PostViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        observeViewModel()
        postsViewModel.fetchPosts()
        handleETListener()
    }

    private fun handleUserInput(input: String) {
        val userInput = input.toIntOrNull()
        Log.d("TAG", "user input in ui = $userInput")
        if (userInput != null) {
            postsViewModel.fetchPostsByUserId(userInput)
        } else {
            postsViewModel.fetchPosts()
        }
    }

    private fun handleETListener() {
        binding.etMain.addTextChangedListener { userInput ->
            handleUserInput(userInput.toString())
        }
    }

    private fun initRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = userAdapter

        }
    }

    private fun observeViewModel() {
        postsViewModel.liveData.observe(this) { state ->
            when (state) {
                is ApiStatus.Loading -> showLoading()
                is ApiStatus.Success -> showPosts(state.data)
                is ApiStatus.Error -> showError(state.message)
            }
        }
        observeUserSearchStatus()
    }

    private fun showError(message: Any) {
        binding.progressBarMain.visibility = View.GONE
        Log.d("TAG", "Error is $message")
        Toast.makeText(this, "Error while getting data", Toast.LENGTH_LONG).show()
    }

    private fun showPosts(posts: List<Post>) {
        binding.progressBarMain.visibility = View.GONE
        userAdapter.submitList(posts)
    }

    private fun showLoading() {
        binding.progressBarMain.visibility = View.VISIBLE
        Log.d("TAG", "Loading")
    }

    private fun observeUserSearchStatus() {
        postsViewModel.userSearchStatus.observe(this) { userSearchStatus ->
            userSearchStatus?.let {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



