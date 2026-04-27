package com.epam.mobitru.screens.showOrder

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentShowOrderBinding
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Show order details
 */
class ShowOrderFragment : BaseFragment(R.layout.fragment_show_order) {

    private val binding by viewBinding(FragmentShowOrderBinding::bind)

    override val viewModel: ShowOrderViewModel by viewModel { parametersOf(args) }

    private val adapter = GroupieAdapter()
    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    private val args: ShowOrderFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.list
                    .collect {
                        adapter.updateAsync(it)
                    }
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.orderId
                    .collect {
                        (requireActivity() as AppCompatActivity).supportActionBar?.title =
                            resources.getString(R.string.order_number_format, it)
                    }
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
        fixButtonCoveredByNavigation(view)
    }

}