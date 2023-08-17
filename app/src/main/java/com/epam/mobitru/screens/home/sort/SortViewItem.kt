package com.epam.mobitru.screens.home.sort

import android.view.View
import androidx.annotation.StringRes
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewSortBinding
import com.epam.mobitru.extentions.applyString
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class SortViewItem(
    @StringRes private val titleRes: Int = 0,
    private val title: String? = "",
    private val isActive: Boolean,
    private val onClick: () -> Unit,
) : BindableItem<ListViewSortBinding>() {
    constructor(@StringRes titleRes: Int, isActive: Boolean, onClick: () -> Unit) : this(
        titleRes,
        "",
        isActive,
        onClick
    )

    constructor(title: String, isActive: Boolean, onClick: () -> Unit) : this(
        0,
        title,
        isActive,
        onClick
    )

    override fun bind(viewBinding: ListViewSortBinding, position: Int) {
        with(viewBinding) {
            title.applyString(titleRes, this@SortViewItem.title)
            title.setTextAppearance(if (isActive) R.style.OrderAppearance_Selected else R.style.OrderAppearance)
            root.setOnClickListener {
                onClick()
            }
            title.compoundDrawablesRelative[2].mutate().setTint(
                title.resources.getColor(
                    if (isActive) {
                        R.color.java
                    } else {
                        R.color.iron
                    }, title.context.theme
                )
            )
        }
    }

    override fun getLayout() = R.layout.list_view_sort

    override fun initializeViewBinding(view: View): ListViewSortBinding {
        return ListViewSortBinding.bind(view)
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is SortViewItem) {
            return titleRes == other.titleRes
        }
        return super.isSameAs(other)
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is SortViewItem) {
            return titleRes == other.titleRes && isActive == other.isActive
        }
        return super.hasSameContentAs(other)
    }
}