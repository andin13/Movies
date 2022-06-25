package com.example.movies.data

sealed interface ServerResponses {
    data class Successful<T>(
        val data : T
    ) : ServerResponses

    data class Error(
        val message: String
    ) : ServerResponses

    object Failure: ServerResponses

    object AccessDenied: ServerResponses

    object TooManyRequests: ServerResponses
}