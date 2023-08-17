package com.epam.mobitru.base

import androidx.annotation.IdRes
import androidx.navigation.NavDirections

sealed class NavCommand {
    data class Direction(val direction: NavDirections) : NavCommand()
    object Back : NavCommand()
    data class BackTo(@IdRes val destinationId: Int, val inclusive: Boolean = false) : NavCommand()
}
