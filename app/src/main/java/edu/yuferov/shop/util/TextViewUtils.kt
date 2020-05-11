package edu.yuferov.shop.util

import android.view.View
import android.widget.TextView
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.Product

fun formatPrice(price: TextView, discountPrice: TextView, model: Product) {
    val context = price.context
    if (model.hasDiscount) {
        price.foreground = context.getDrawable(R.drawable.cross_out)
        discountPrice.visibility = View.VISIBLE
        discountPrice.text =
            context.getString(R.string.template_price, model.totalPrice.value)
    } else {
        price.foreground = null
        discountPrice.text = ""
        discountPrice.visibility = View.GONE
    }
    price.text = context.getString(R.string.template_price, model.price.value)
}
