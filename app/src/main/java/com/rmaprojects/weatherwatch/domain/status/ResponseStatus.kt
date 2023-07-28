package com.rmaprojects.weatherwatch.domain.status

sealed class ResponseStatus<T> {
    data class Success<T>(val data: T): ResponseStatus<T>()
    data class Error<Nothing>(val message: String): ResponseStatus<Nothing>()
}