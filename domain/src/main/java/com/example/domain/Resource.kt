package com.example.domain

sealed class Resource<out R> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val message: String?) : Resource<Nothing>()
}
