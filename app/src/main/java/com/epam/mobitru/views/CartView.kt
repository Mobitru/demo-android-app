package com.epam.mobitru.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ViewCartBinding

class CartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyle) {

    private val binding: ViewCartBinding

    init {
        binding = ViewCartBinding.inflate(LayoutInflater.from(context), this, true)
        setCounter(-1)
    }


    fun setCounter(value: Int) {
        binding.cartTitle.text = resources.getString(R.string.cart_toolbar_title, value)
    }

    fun setClickListener(listener: () -> Unit) {
        binding.cartTitle.setOnClickListener { listener() }
    }
}