package com.gawel.babyjudas.core.models

sealed class State<T>(val data: T?, val message: String?) {
    class Loading<T>(message: String? = null) : State<T>(null, message)
    class Success<T>(data: T) : State<T>(data, null)
    class Error<T>(message: String) : State<T>(null, message)
    class Empty<T>() : State<T>(null, null)
}
