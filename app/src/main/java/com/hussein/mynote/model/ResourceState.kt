package com.hussein.mynote.model

sealed class ResourceState<out T> {

    class Loading<T> : ResourceState<T>()
    data class Success<T>(val data :T) : ResourceState<T>()
    data class Error<T>(val data :Any) : ResourceState<T>()


}
