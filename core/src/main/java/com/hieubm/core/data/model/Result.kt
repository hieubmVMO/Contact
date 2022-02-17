package com.hieubm.core.data.model

sealed class Result<out O> {
    data class Success<out D>(val data: D) : Result<D>()

    data class Failure(val exception: Exception) : Result<Nothing>()
}