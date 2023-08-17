package com.epam.mobitru.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.epam.mobitru.MainActivity
import com.epam.mobitru.R
import com.epam.mobitru.base.BaseFragment
import com.epam.mobitru.base.BaseNavigationHandler
import com.epam.mobitru.base.NavCommand
import com.epam.mobitru.base.TopFragmentNavigator
import com.epam.mobitru.databinding.FragmentMainBinding
import com.epam.mobitru.util.viewBinding
import com.epam.mobitru.views.CartView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main application fragment, provides bottom navigation
 */
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)
    override val viewModel: MainViewModel by viewModel()
    private var cartView: CartView? = null
    private var isCartShown = true
    override val navigator: BaseNavigationHandler<BaseFragment> = TopFragmentNavigator()

    private val destinationListener: (
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) -> Unit =
        { _, destination, _ ->
            if (destination.id in listOf(R.id.homeFragment, R.id.ordersFragment)) {
                isCartShown = true
                showLogo()
            } else {
                isCartShown = false
                hideLogo(destination.label)
            }
            requireActivity().invalidateOptionsMenu()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = getNavController()
        binding?.bottomNav?.setupWithNavController(navController)
        setupMenu()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.cartCounter.collect {
                cartView?.setCounter(it)
            }
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.menu_cart).isVisible = isCartShown
                cartView?.setCounter(viewModel.cartCounter.value)
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_cart, menu)
                cartView = (menu.findItem(R.id.menu_cart).actionView as CartView)
                cartView?.setClickListener {
                    navigator.navigate(NavCommand.Direction(MainFragmentDirections.actionMainFragmentToCartFragment()))
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun getNavController(): NavController {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.navController
    }

    override fun onResume() {
        super.onResume()
        val navController = getNavController()
        navController.addOnDestinationChangedListener(destinationListener)
    }

    override fun onPause() {
        val navController = getNavController()
        navController.removeOnDestinationChangedListener(destinationListener)
        val supportActionBar = (requireActivity() as MainActivity).supportActionBar
        supportActionBar?.setDisplayUseLogoEnabled(false)
        super.onPause()
    }
}