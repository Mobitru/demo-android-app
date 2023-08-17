package com.epam.mobitru.extentions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                Timber.i("On Passed : $value")
                lastTime = currentTime
                emit(value)
            } else {
                Timber.i("On Throttled : $value")
            }
        }
    }
}