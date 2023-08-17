package com.epam.mobitru.views.listview

import android.view.View
import androidx.core.view.isVisible
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewUserBinding
import com.epam.mobitru.models.User
import com.xwray.groupie.viewbinding.BindableItem

class UserViewItem(
    private val user: User?,
    private val onClick: (() -> Unit)? = null,
) : BindableItem<ListViewUserBinding>() {
    override fun bind(viewBinding: ListViewUserBinding, position: Int) {
        viewBinding.apply {
            if (onClick != null) {
                edit.setOnClickListener {
                    onClick.invoke()
                }
                edit.isVisible = true
            } else {
                edit.isVisible = false
            }
            if (user == null) {
                name.text = "Please signin"
                return
            }
            name.text = user.fullName
            email.text = user.email
            address.text = user.address
        }
    }

    override fun getLayout() = R.layout.list_view_user

    override fun initializeViewBinding(view: View): ListViewUserBinding {
        return ListViewUserBinding.bind(view)
    }
}