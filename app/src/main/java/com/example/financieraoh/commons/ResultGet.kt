package com.example.financieraoh.commons

import java.io.Serializable

sealed class ResultGet<out R> : Serializable {
    data class Loading(val data: Any? = null) : ResultGet<Nothing>()
    data class Error(val exception: Exception) : ResultGet<Nothing>()
    data class Success<T>(val data: T) : ResultGet<T>()
    data class Empty(val data: Any? = null) : ResultGet<Nothing>()
}