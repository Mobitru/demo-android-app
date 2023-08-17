package com.epam.mobitru.views

import android.text.Editable
import android.text.TextWatcher

fun interface TextWatcherAfterChanged : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) = onChanged(s.toString())

    fun onChanged(s: String)
}