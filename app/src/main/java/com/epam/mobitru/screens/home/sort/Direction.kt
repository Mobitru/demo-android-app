package com.epam.mobitru.screens.home.sort

import androidx.annotation.DrawableRes
import com.epam.mobitru.R

enum class Direction(@DrawableRes val image: Int) {
    Asc(R.drawable.ic_sort_up),
    Desc(R.drawable.ic_sort_down),
}