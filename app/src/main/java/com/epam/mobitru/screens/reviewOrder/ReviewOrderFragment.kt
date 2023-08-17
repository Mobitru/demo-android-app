package com.epam.mobitru.screens.reviewOrder

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentReviewOrderBinding
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Asks user to confirm order proceed. Requests fill profile, if it is empty
 */
class ReviewOrderFragment : BaseFragment(R.layout.fragment_review_order) {

    private val binding by viewBinding(FragmentReviewOrderBinding::bind)

    override val viewModel: ReviewOrderViewModel by viewModel()

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
            confirm.setOnClickListener {
                viewModel.confirmOrder()
                navigator.navigate(NavCommand.Direction(ReviewOrderFragmentDirections.actionReviewOrderFragmentToCompletedOrderFragment()))
            }
        }
        fixButtonCoveredByNavigation(view)
    }

}