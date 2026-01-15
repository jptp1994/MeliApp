package com.example.meliapp.data.exception

sealed class NetworkException : Exception() {
    data class HttpError(val code: Int, val errorMessage: String) : NetworkException()
    data class NoInternetError(val errorMessage: String) : NetworkException()
    data class UnknownError(val errorMessage: String) : NetworkException()
}