package com.epam.mobitru.screens.cart

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewCartProductBinding
import com.epam.mobitru.models.Product
import com.epam.mobitru.util.CurrencyFormatter
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class CartViewItem(
    private val product: Product,
    private val counter: Int,
    private val onAction: (Actions) -> Unit
) : BindableItem<ListViewCartProductBinding>() {

    companion object {
        const val MAX_ITEM_COUNT = 10
    }

    override fun bind(viewBinding: ListViewCartProductBinding, position: Int) {
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
            counter.text = "${this@CartViewItem.counter}"
            if (this@CartViewItem.counter == 1) {
                minus.isVisible = false
                remove.isVisible = true
            } else {
                minus.isVisible = true
                remove.isVisible = false
            }

            plus.isEnabled = this@CartViewItem.counter != MAX_ITEM_COUNT

            minus.setOnClickListener {
                onAction(Actions.MINUS)
            }
            plus.setOnClickListener {
                onAction(Actions.PLUS)
            }
            remove.setOnClickListener {
                onAction(Actions.REMOVE)
            }
        }
    }

    override fun getLayout() = R.layout.list_view_cart_product

    override fun initializeViewBinding(view: View): ListViewCartProductBinding {
        val productBinding = ListViewCartProductBinding.bind(view)
        productBinding.priceOriginal.paintFlags =
            productBinding.priceOriginal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return productBinding
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is CartViewItem) {
            return product.id == other.product.id
        }
        return super.isSameAs(other)
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is CartViewItem) {
            return product == other.product && counter == other.counter
        }
        return super.hasSameContentAs(other)
    }

    enum class Actions {
        PLUS, MINUS, REMOVE
    }
}