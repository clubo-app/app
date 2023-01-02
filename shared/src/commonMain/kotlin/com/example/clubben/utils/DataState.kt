package com.example.clubben.utils

import com.example.clubben.ui.toast.ToastViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class DataState<out Success, out Failure> where Failure : Throwable {
    data class Success<out Success>(val value: Success) : DataState<Success, Nothing>()
    data class Failure<out Failure : Throwable>(val error: Failure) : DataState<Nothing, Failure>()

    fun <R> fold(
        onSuccess: (value: Success) -> R,
        onFailure: (error: Failure) -> R
    ): R {
        return when (this) {
            is DataState.Success -> onSuccess(value)
            is DataState.Failure -> onFailure(error)
        }
    }

    fun getOrThrow(): Success {
        return when (this) {
            is DataState.Success -> value
            is DataState.Failure -> throw error
        }
    }
}

inline fun <T> catchApiError(block: () -> T): DataState<T, ApiError> {
    val toastQueue = object : KoinComponent {
        val im: ToastViewModel by inject()
    }.im

    return try {
        DataState.Success(block.invoke())
    } catch (e: ApiError) {
        println("ApiError: $e")
        toastQueue.add(e)
        DataState.Failure(e)
    } catch (e: Exception) {
        println("Exception: $e")
        val apiError: ApiError =
            if (e.message != null) ApiError(message = e.message!!) else internalServerError
        toastQueue.add(apiError)
        DataState.Failure(apiError)
    }
}