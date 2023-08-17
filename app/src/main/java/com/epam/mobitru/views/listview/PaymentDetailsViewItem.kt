package com.epam.mobitru.views.listview

import android.view.View
import com.epam.mobitru.R
import com.epam.mobitru.databinding.ListViewPaymentDetailsBinding
import com.epam.mobitru.models.PaymentDetails
import com.epam.mobitru.util.CurrencyFormatter
import com.xwray.groupie.viewbinding.BindableItem

class PaymentDetailsViewItem(
    private val paymentDetails: PaymentDetails
) : BindableItem<ListViewPaymentDetailsBinding>() {
    override fun bind(viewBinding: ListViewPaymentDetailsBinding, position: Int) {
        viewBinding.apply {
            packagingFeeValue.text = CurrencyFormatter.formatPrice(paymentDetails.packagingFee)
            subtotalValue.text = CurrencyFormatter.formatPrice(paymentDetails.subtotal)
            deliveryFeeValue.text = CurrencyFormatter.formatPrice(paymentDetails.deliveryFee)
            discountValue.text = CurrencyFormatter.formatPrice(paymentDetails.discount)
            totalValue.text = CurrencyFormatter.formatPrice(paymentDetails.total)
        }
    }

    override fun getLayout() = R.layout.list_view_payment_details

    override fun initializeViewBinding(view: View): ListViewPaymentDetailsBinding {
        return ListViewPaymentDetailsBinding.bind(view)
    }
}