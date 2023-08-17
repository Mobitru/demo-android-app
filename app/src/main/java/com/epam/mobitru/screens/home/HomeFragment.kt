package com.epam.mobitru.screens.home

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentHomeBinding
import com.epam.mobitru.extentions.getKey
import com.epam.mobitru.main.MainFragmentDirections
import com.epam.mobitru.screens.home.sort.SortViewModel
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main products page
 */
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override val viewModel: HomeViewModel by viewModel()

    private val adapter = GroupieAdapter()

    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    private var scrollToStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToVm()
    }

    private fun subscribeToVm() {
        subscribeList()
        subscribeTitle()
        subscribeSort()
    }

    private fun subscribeSort() {
        lifecycleScope.launchWhenResumed {
            viewModel.selectedOrder.collect {
                binding?.apply {
                    sortBy.setText(it.first.label)
                    scrollToStart = true
                    sortBy.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        it.second.image, 0, 0, 0
                    )

                    val descriptionResId = SortViewModel.SORT_OPTIONS.getKey(it)
                    sortBy.contentDescription = getString(descriptionResId)
                    ViewCompat.replaceAccessibilityAction(
                        sortBy,
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
                        getString(R.string.sort_change),
                        null
                    )
                }
            }
        }
    }

    private fun subscribeTitle() {
        lifecycleScope.launchWhenResumed {
            viewModel.title.collect {
                binding?.category?.text = it
            }
        }
    }

    private fun subscribeList() {
        lifecycleScope.launchWhenResumed {
            viewModel.list.collect {
                adapter.updateAsync(it) {
                    if (scrollToStart) {
                        binding?.productList?.scrollToPosition(0)
                        scrollToStart = false
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            productList.layoutManager = GridLayoutManager(
                requireContext(),
                resources.getInteger(R.integer.home_span),
                GridLayoutManager.VERTICAL,
                false
            )
            productList.adapter = adapter
            sortBy.setOnClickListener {
                navigator.navigate(NavCommand.Direction(MainFragmentDirections.actionMainFragmentToSortFragment()))
            }
        }
    }

}