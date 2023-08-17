package com.epam.mobitru.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.epam.mobitru.MainActivity
import com.epam.mobitru.R

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId),
    WithViewModel<BaseViewModel> {

    private val toastHandler = ToastHandlerImpl<BaseFragment>()

    abstract val navigator: BaseNavigationHandler<BaseFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toastHandler.subscribe(this)
        navigator.subscribe(this)
        lifecycle.addObserver(viewModel)
    }

    protected fun fixButtonCoveredByNavigation(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                bottomMargin = insets.bottom
                rightMargin = insets.right
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    protected fun showLogo() {
        val supportActionBar = (requireActivity() as MainActivity).supportActionBar
        supportActionBar?.let {
            it.setLogo(R.drawable.img_logo)
            it.setDisplayUseLogoEnabled(true)
            it.title = ""
        }
    }

    protected fun hideLogo(title: CharSequence?) {
        val supportActionBar = (requireActivity() as MainActivity).supportActionBar
        supportActionBar?.let {
            it.setDisplayUseLogoEnabled(false)
            it.title = title
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }
}