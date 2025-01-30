package com.mangoobyte.audiobookstest.data

sealed class UiState<T>(
    val data: T? = null
) {
    class Loading<T> : UiState<T>()
    class Success<T>(data: T?) : UiState<T>(data)
    class Error<T>(data: T?) : UiState<T>(data)
}