package com.epam.mobitru.screens.cart

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.codescanner.StringListResultContract
import com.epam.mobitru.databinding.FragmentCartBinding
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Cart management page
 */
class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    override val viewModel: CartViewModel by viewModel()

    private val adapter = GroupieAdapter()

    private val getPromoFromQr = registerForActivityResult(StringListResultContract()) {
        it?.let {
            viewModel.applyPromo(it.first())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.list.collect {
                handleList(it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.cartPromo.collect {
                handlePromo(it, viewModel.list.value.isEmpty())
            }
        }
    }

    private fun handlePromo(it: String?, empty: Boolean) {
        binding?.apply {
            if (empty) {
                applyPromoCode.isVisible = false
                promoApplied.isVisible = false
                return
            }
            if (TextUtils.isEmpty(it)) {
                applyPromoCode.isVisible = true
                promoApplied.isVisible = false
            } else {
                promoApplied.isVisible = true
                applyPromoCode.isVisible = false
                promoCode.text = it
            }
        }
    }

    private fun handleList(it: List<Group>) {
        adapter.updateAsync(it)
        binding?.apply {
            if (it.isEmpty()) {
                cartList.isVisible = false
                continueToCheckout.isVisible = false
                emptyCart.isVisible = true
                applyPromoCode.isVisible = false
                promoApplied.isVisible = false
            } else {
                cartList.isVisible = true
                continueToCheckout.isVisible = true
                emptyCart.isVisible = false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        handleList(viewModel.list.value)
        handlePromo(viewModel.cartPromo.value, viewModel.list.value.isEmpty())
        fixButtonCoveredByNavigation(view)
    }

    private fun setupViews() {
        binding?.apply {
            cartList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            cartList.adapter = adapter
            continueToCheckout.setOnClickListener {
                navigator.navigate(NavCommand.Direction(CartFragmentDirections.actionCartFragmentToReviewOrderFragment()))
            }
            continueToShopping.setOnClickListener {
                navigator.navigate(NavCommand.Direction(CartFragmentDirections.actionBackToShopping()))
            }
            applyPromoCode.setOnClickListener {
                getPromoFromQr.launch(Unit)
            }
            removePromo.setOnClickListener {
                viewModel.removePromo()
            }
        }
    }

}