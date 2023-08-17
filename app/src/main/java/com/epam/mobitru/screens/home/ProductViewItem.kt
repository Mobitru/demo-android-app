package com.epam.mobitru.screens.home

import android.graphics.Paint
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewProductBinding
import com.epam.mobitru.extentions.accessibilityFocus
import com.epam.mobitru.models.Product
import com.epam.mobitru.util.CurrencyFormatter
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class ProductViewItem(
    private val product: Product,
    private val isAdded: Boolean,
    private val requestFocus : Boolean,
    private val onAddToCartClick: () -> Unit,
    private val onItemClick: () -> Unit,
) : BindableItem<ListViewProductBinding>() {

    override fun bind(viewBinding: ListViewProductBinding, position: Int) {
        with(viewBinding) {
            title.text = product.name
            Picasso.get()
                .load(product.assetPath)
                .fit()
                .into(viewBinding.image)
            val productPrice = CurrencyFormatter.formatPrice(product.price)
            if (product.isDiscountExist) {
                regularItem.isVisible = false
                discountItem.isVisible = true
                val priceBeforeDiscount =
                    CurrencyFormatter.formatPrice(product.discountPrice ?: 0.0)
                priceDiscount.text = priceBeforeDiscount
                priceOriginal.text = productPrice
                discountValue.text = product.discountValue
                root.contentDescription = root.resources.getString(
                    R.string.product_description_discount,
                    product.name,
                    product.discountValue,
                    productPrice,
                    priceBeforeDiscount
                )
            } else {
                regularItem.isVisible = true
                discountItem.isVisible = false
                price.text = productPrice
                root.contentDescription = root.resources.getString(
                    R.string.product_description_no_discount,
                    product.name,
                    productPrice
                )
            }

            addToCart.contentDescription =
                addToCart.resources.getString(R.string.add_item_to_cart_description, product.name)
            removeFromCart.contentDescription = removeFromCart.resources.getString(
                R.string.remove_item_from_cart_description,
                product.name
            )

            addToCart.isVisible = !isAdded
            removeFromCart.isVisible = isAdded

            if (requestFocus) {
                if (isAdded) {
                    removeFromCart.accessibilityFocus()
                } else {
                    addToCart.accessibilityFocus()
                }
            }

            root.setOnClickListener {
                onItemClick()
            }
            addToCart.setOnClickListener {
                onAddToCartClick()
            }
            removeFromCart.setOnClickListener {
                onAddToCartClick()
            }
        }
    }

    override fun getLayout() = R.layout.list_view_product

    override fun getId(): Long {
        return product.id.hashCode().toLong()
    }

    override fun initializeViewBinding(view: View): ListViewProductBinding {
        val bind = ListViewProductBinding.bind(view)
        bind.priceOriginal.paintFlags = bind.priceOriginal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return bind
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is ProductViewItem) {
            return this.product.id == other.product.id
        }
        return super.isSameAs(other)
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is ProductViewItem) {
            return this.product == other.product && this.isAdded == other.isAdded
        }
        return super.hasSameContentAs(other)
    }
}