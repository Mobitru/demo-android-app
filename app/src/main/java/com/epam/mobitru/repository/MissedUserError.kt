package com.epam.mobitru.repository

class MissedUserError(message: String, cause: Throwable) : RepositoryError(message, cause)