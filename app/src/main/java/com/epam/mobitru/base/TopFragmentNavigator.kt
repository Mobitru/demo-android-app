package com.epam.mobitru.base

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.epam.mobitru.R

class TopFragmentNavigator<F> :
    BaseNavigationHandler<F>() where F : Fragment, F : WithViewModel<*> {
    override fun navigate(direction: NavCommand) {
        when (direction) {
            is NavCommand.Direction -> findController().navigate(direction.direction)
            is NavCommand.Back -> findController().popBackStack()
            is NavCommand.BackTo -> findController().popBackStack(
                direction.destinationId, direction.inclusive
            )
        }
    }

    override fun findController() =
        fragment.requireActivity().findNavController(R.id.nav_host_fragment_content_main)
}