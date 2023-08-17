package com.epam.mobitru.views.listview

import android.view.View
import androidx.annotation.StringRes
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewItemBinding
import com.epam.mobitru.extentions.applyString
import com.xwray.groupie.viewbinding.BindableItem

class ItemViewItem(
    @StringRes private val titleRes: Int = 0,
    private val title: String? = "",
    private val onClick: () -> Unit,
) : BindableItem<ListViewItemBinding>() {
    constructor(@StringRes titleRes: Int, onClick: () -> Unit) : this(titleRes, "", onClick)
    constructor(title: String, onClick: () -> Unit) : this(0, title, onClick)

    override fun bind(viewBinding: ListViewItemBinding, position: Int) {
        with(viewBinding) {
            title.applyString(titleRes, this@ItemViewItem.title)
            root.setOnClickListener {
                onClick()
            }
        }
    }

    override fun getLayout() = R.layout.list_view_item

    override fun initializeViewBinding(view: View): ListViewItemBinding {
        return ListViewItemBinding.bind(view)
    }
}