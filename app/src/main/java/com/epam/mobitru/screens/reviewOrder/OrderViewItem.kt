package com.epam.mobitru.screens.reviewOrder

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewOrderProductBinding
import com.epam.mobitru.models.Product
import com.epam.mobitru.util.CurrencyFormatter
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class OrderViewItem(
    private val product: Product,
    private val counter: Int,
) : BindableItem<ListViewOrderProductBinding>() {

    override fun bind(viewBinding: ListViewOrderProductBinding, position: Int) {
        with(viewBinding) {
            title.text = product.name
            Picasso.get()
                .load(product.assetPath)
                .fit()
                .into(viewBinding.image)
            if (product.isDiscountExist) {
                regularItem.isVisible = false
                discountItem.isVisible = true
                priceDiscount.text = CurrencyFormatter.formatPrice(product.discountPrice ?: 0.0)
                priceOriginal.text = CurrencyFormatter.formatPrice(product.price)
                discountValue.text = product.discountValue
            } else {
                regularItem.isVisible = true
                discountItem.isVisible = false
                price.text = CurrencyFormatter.formatPrice(product.price)
            }
            counter.text = counter.resources.getString(
                R.string.order_item_quantity,
                this@OrderViewItem.counter
            )
        }
    }

    override fun getLayout() = R.layout.list_view_order_product

    override fun initializeViewBinding(view: View): ListViewOrderProductBinding {
        val productBinding = ListViewOrderProductBinding.bind(view)
        productBinding.priceOriginal.paintFlags =
            productBinding.priceOriginal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return productBinding
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is OrderViewItem) {
            return product.id == other.product.id
        }
        return super.isSameAs(other)
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is OrderViewItem) {
            return product == other.product && counter == other.counter
        }
        return super.hasSameContentAs(other)
    }
}