package com.epam.mobitru.screens.editProfile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentEditProfileBinding
import com.epam.mobitru.extentions.requireEditText
import com.epam.mobitru.util.viewBinding
import com.epam.mobitru.views.TextWatcherAfterChanged
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Change user name, address etc
 */
class EditProfileFragment : BaseFragment(R.layout.fragment_edit_profile) {

    private val binding by viewBinding(FragmentEditProfileBinding::bind)

    override val viewModel: EditProfileViewModel by viewModel()
    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToVm()
        handleBackEvent()
    }

    private fun subscribeToVm() {
        lifecycleScope.launchWhenResumed {
            viewModel.firstNameError.collect {
                binding?.firstName?.error = it
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.lastNameError.collect {
                binding?.lastName?.error = it
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.addressError.collect {
                binding?.address?.error = it
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.email.collect {
                binding?.email?.editText?.setText(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreState()
        subscribeViews()

        handleUpEvent()
        fixButtonCoveredByNavigation(view)
    }

    private fun handleBackEvent() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    if (isCameFromReview()) {
                        navigator.navigate(NavCommand.BackTo(R.id.cartFragment))
                    } else {
                        navigator.navigate(NavCommand.Back)
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun handleUpEvent() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (isCameFromReview()) {
                    navigator.navigate(NavCommand.BackTo(R.id.cartFragment))
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun isCameFromReview() =
        navigator.findController().previousBackStackEntry?.destination?.id == R.id.reviewOrderFragment

    private fun subscribeViews() {
        binding?.apply {
            save.setOnClickListener {
                viewModel.save()
            }
            firstName.requireEditText().addTextChangedListener(TextWatcherAfterChanged { s ->
                viewModel.onFirstNameUpdated(s)
            })
            lastName.requireEditText()
                .addTextChangedListener(TextWatcherAfterChanged { s -> viewModel.onLastNameUpdated(s) })
            address.requireEditText()
                .addTextChangedListener(TextWatcherAfterChanged { s -> viewModel.onAddressUpdated(s) })
        }
    }

    private fun restoreState() {
        binding?.apply {
            firstName.editText?.setText(viewModel.firstName.value)
            lastName.editText?.setText(viewModel.lastName.value)
            address.editText?.setText(viewModel.address.value)
        }

    }
}