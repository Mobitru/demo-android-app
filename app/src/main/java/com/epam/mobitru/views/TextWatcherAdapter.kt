package com.epam.mobitru.views

import android.text.Editable
import android.text.TextWatcher

/**
 * Base class for scenarios where user wants to implement only one method of
 * [android.text.TextWatcher].
 * Copy of internal material class
 */

open class TextWatcherAdapter : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }
}