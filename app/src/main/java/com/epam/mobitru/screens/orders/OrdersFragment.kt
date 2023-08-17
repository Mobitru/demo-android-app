package com.epam.mobitru.screens.orders

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentOrdersBinding
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Show list of orders
 */
class OrdersFragment : BaseFragment(R.layout.fragment_orders) {

    private val binding by viewBinding(FragmentOrdersBinding::bind)

    override val viewModel: OrdersViewModel by viewModel()

    private val adapter = GroupieAdapter()

    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.list.collect {
                adapter.updateAsync(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            list.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            list.adapter = adapter
        }
    }

}