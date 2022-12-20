package com.example.clubben.utils

import com.example.clubben.domain.toast.ToastQueue
import dev.gitlive.firebase.auth.FirebaseAuthException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed class DataState<out Success, out Failure> {
    data class Success<out Success>(val value: Success) : DataState<Success, Nothing>()
    data class Failure<out Failure>(val error: Failure) : DataState<Nothing, Failure>()
}

inline fun <T> catchApiError(block: () -> T): DataState<T, ApiError> {
    val toastQueue = object : KoinComponent {
        val im: ToastQueue by inject()
    }.im

    return try {
        DataState.Success(block())
    } catch (e: ApiError) {
        toastQueue.add(e)
        DataState.Failure(e)
    } catch (e: FirebaseAuthException) {
        val apiError: ApiError =
            if (e.message != null) ApiError(message = e.message!!) else internalServerError
        toastQueue.add(apiError)
        DataState.Failure(apiError)
    } catch (e: Exception) {
        DataState.Failure(internalServerError)
    }
}