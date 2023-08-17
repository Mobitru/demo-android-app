package com.epam.mobitru.screens.completedOrder

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentCompletedOrderBinding
import com.epam.mobitru.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Confirmation order fragment
 */
class CompletedOrderFragment : BaseFragment(R.layout.fragment_completed_order) {

    private val binding by viewBinding(FragmentCompletedOrderBinding::bind)

    override val viewModel: CompletedOrderViewModel by viewModel()
    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding?.apply {
            goBack.setOnClickListener {
                navigator.navigate(NavCommand.BackTo(R.id.MainFragment))
            }
        }
        fixButtonCoveredByNavigation(view)
    }
}