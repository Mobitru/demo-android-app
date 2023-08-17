package com.epam.mobitru.repository

open class RepositoryError(message: String, cause: Throwable) : RuntimeException(message, cause)