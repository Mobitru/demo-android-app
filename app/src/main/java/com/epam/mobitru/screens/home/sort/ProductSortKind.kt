package com.epam.mobitru.screens.home.sort

import androidx.annotation.StringRes
import com.epam.mobitru.R

enum class ProductSortKind(@StringRes val label: Int) {
    ByPrice(R.string.sort_by_price),
    ByName(R.string.sort_by_name),
}