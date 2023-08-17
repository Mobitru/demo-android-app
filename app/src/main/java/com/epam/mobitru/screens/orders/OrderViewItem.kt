package com.epam.mobitru.screens.orders

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewOrderBinding
import com.epam.mobitru.models.Order
import com.epam.mobitru.models.OrderState
import com.epam.mobitru.models.ProductWithQuantity
import com.epam.mobitru.util.CurrencyFormatter
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

class OrderViewItem(
    private val order: Order,
    private val onClick: () -> Unit,
) : BindableItem<ListViewOrderBinding>() {

    override fun bind(viewBinding: ListViewOrderBinding, position: Int) {
        with(viewBinding) {
            title.text = title.resources.getString(R.string.order_number_format, order.id)
            state.isVisible = order.state == OrderState.IN_PROGRESS
            price.text = CurrencyFormatter.formatPrice(order.paymentDetails.total)
            drawProductLine(line1, line1count, order.products.getOrNull(0))
            drawProductLine(line2, line2count, order.products.getOrNull(1))
            root.setOnClickListener {
                onClick()
            }
        }
    }

    private fun drawProductLine(name: TextView, count: TextView, product: ProductWithQuantity?) {
        if (product != null) {
            name.isVisible = true
            name.text = product.product.name
            if (product.quantity > 1) {
                count.isVisible = true
                count.text =
                    count.resources.getString(R.string.order_count_format, product.quantity)
            } else {
                count.isVisible = false
            }
        } else {
            name.isVisible = false
            count.isVisible = false
        }
    }

    override fun getLayout() = R.layout.list_view_order

    override fun initializeViewBinding(view: View): ListViewOrderBinding {
        return ListViewOrderBinding.bind(view)
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is OrderViewItem) {
            return order.id == other.order.id
        }
        return super.isSameAs(other)
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is OrderViewItem) {
            return order == other.order
        }
        return super.hasSameContentAs(other)
    }
}