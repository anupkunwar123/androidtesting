package com.anupkunwar.servicelocator

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()

    object Loading : Result<Nothing>()

    data class Error(
        private val errorCode: Int,
        private val errorMessage: String
    ) : Result<Nothing>()

}