package com.epam.mobitru.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.UiThread

class ContentLoadingFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    // These fields should only be accessed on the UI thread.
    private var mStartTime: Long = -1
    private var mPostedHide = false
    private var mPostedShow = false
    private var mDismissed = false
    private val mDelayedHide = Runnable {
        mPostedHide = false
        mStartTime = -1
        visibility = GONE
    }
    private val mDelayedShow = Runnable {
        mPostedShow = false
        if (!mDismissed) {
            mStartTime = System.currentTimeMillis()
            visibility = VISIBLE
        }
    }

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        removeCallbacks()
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(mDelayedHide)
        removeCallbacks(mDelayedShow)
    }

    /**
     * Hide the progress view if it is visible. The progress view will not be
     * hidden until it has been shown for at least a minimum show time. If the
     * progress view was not yet visible, cancels showing the progress view.
     *
     *
     * This method may be called off the UI thread.
     */
    fun hide() {
        // This method used to be synchronized, presumably so that it could be safely called off
        // the UI thread; however, the referenced fields were still accessed both on and off the
        // UI thread, e.g. not thread-safe. Now we hand-off everything to the UI thread.
        post { hideOnUiThread() }
    }

    @UiThread
    private fun hideOnUiThread() {
        mDismissed = true
        removeCallbacks(mDelayedShow)
        mPostedShow = false
        val diff = System.currentTimeMillis() - mStartTime
        if (diff >= MIN_SHOW_TIME_MS || mStartTime == -1L) {
            // The progress spinner has been shown long enough
            // OR was not shown yet. If it wasn't shown yet,
            // it will just never be shown.
            visibility = GONE
        } else {
            // The progress spinner is shown, but not long enough,
            // so put a delayed message in to hide it when its been
            // shown long enough.
            if (!mPostedHide) {
                postDelayed(mDelayedHide, MIN_SHOW_TIME_MS - diff)
                mPostedHide = true
            }
        }
    }

    /**
     * Show the progress view after waiting for a minimum delay. If
     * during that time, hide() is called, the view is never made visible.
     *
     *
     * This method may be called off the UI thread.
     */
    fun show() {
        // This method used to be synchronized, presumably so that it could be safely called off
        // the UI thread; however, the referenced fields were still accessed both on and off the
        // UI thread, e.g. not thread-safe. Now we hand-off everything to the UI thread.
        post { showOnUiThread() }
    }

    @UiThread
    private fun showOnUiThread() {
        // Reset the start time.
        mStartTime = -1
        mDismissed = false
        removeCallbacks(mDelayedHide)
        mPostedHide = false
        if (!mPostedShow) {
            postDelayed(mDelayedShow, MIN_DELAY_MS.toLong())
            mPostedShow = true
        }
    }

    companion object {
        private const val MIN_SHOW_TIME_MS = 500
        private const val MIN_DELAY_MS = 500
    }
}