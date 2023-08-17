package com.epam.mobitru.screens.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentAccountBinding
import com.epam.mobitru.util.viewBinding
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * General user account page
 */
class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private val binding by viewBinding(FragmentAccountBinding::bind)

    override val viewModel: AccountViewModel by viewModel()

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
            accountList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            accountList.adapter = adapter
            logout.setOnClickListener {
                viewModel.logout()
            }
        }
    }
}