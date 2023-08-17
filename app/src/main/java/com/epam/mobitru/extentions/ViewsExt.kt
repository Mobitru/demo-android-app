package com.epam.mobitru.extentions

import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.postDelayed
import com.google.android.material.textfield.TextInputLayout

fun TextView.applyString(
    @StringRes titleRes: Int = 0,
    title: String? = ""
) {
    if (titleRes != 0) {
        this.setText(titleRes)
    } else {
        this.text = title
    }
}

fun TextInputLayout.requireEditText() = this.editText!!

fun View.accessibilityFocus(): View {
    this.postDelayed(300) {
        this.performAccessibilityAction(AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null)
        this.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED)
        this.requestFocus()
    }
    return this
}