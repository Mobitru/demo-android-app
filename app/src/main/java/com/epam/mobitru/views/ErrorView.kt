package com.epam.mobitru.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.epam.mobitru.databinding.ViewErrorBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyle) {

    private val binding: ViewErrorBinding

    init {
        binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setCloseClickListener(l: OnClickListener?) {
        binding.closeButton.setOnClickListener(l)
    }
}