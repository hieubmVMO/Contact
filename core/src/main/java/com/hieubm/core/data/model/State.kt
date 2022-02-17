package com.hieubm.core.data.model

sealed class State<out O> {
    data class Loading(val isLoading: Boolean) : State<Nothing>()

    data class Failure(val exception: Exception) : State<Nothing>()
}