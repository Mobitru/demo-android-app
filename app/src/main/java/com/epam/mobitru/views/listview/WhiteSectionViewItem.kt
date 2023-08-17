package com.epam.mobitru.views.listview

import android.view.View
import androidx.annotation.StringRes
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewWhiteSectionBinding
import com.epam.mobitru.extentions.applyString
import com.xwray.groupie.viewbinding.BindableItem

class WhiteSectionViewItem(
    @StringRes private val titleRes: Int = 0,
    private val title: String? = "",
) : BindableItem<ListViewWhiteSectionBinding>() {
    constructor(@StringRes titleRes: Int) : this(titleRes, "")
    constructor(title: String) : this(0, title)

    override fun bind(viewBinding: ListViewWhiteSectionBinding, position: Int) {
        viewBinding.title.applyString(titleRes, title)
    }

    override fun getLayout() = R.layout.list_view_white_section

    override fun initializeViewBinding(view: View): ListViewWhiteSectionBinding {
        return ListViewWhiteSectionBinding.bind(view)
    }
}