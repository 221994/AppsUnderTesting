package com.example.worldelite.util

sealed class ApiStatus<out T> {

        object Loading : ApiStatus<Nothing>()
        data class Success<out T>(val data: T) : ApiStatus<T>()
        data class Error(val message: String) : ApiStatus<Nothing>()

}