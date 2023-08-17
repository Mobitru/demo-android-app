package com.epam.mobitru.screens.home.sort

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentSortBinding
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Select soring for home products page
 */
class SortFragment : BaseFragment(R.layout.fragment_sort) {

    private val binding by viewBinding(FragmentSortBinding::bind)

    override val viewModel: SortViewModel by viewModel()

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
            chooseOrderList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            chooseOrderList.adapter = adapter
            apply.setOnClickListener {
                viewModel.apply()
                navigator.navigate(NavCommand.Back)
            }
        }
        fixButtonCoveredByNavigation(view)
    }
}