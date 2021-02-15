package com.vivek.jettasks.domain.data

sealed class DataState<T> {

    class Empty<T> : DataState<T>()

    class Loading<T> : DataState<T>()

    class Success<T>(val data: T) : DataState<T>()

    class Error<T>(val message: String) : DataState<T>()
}
