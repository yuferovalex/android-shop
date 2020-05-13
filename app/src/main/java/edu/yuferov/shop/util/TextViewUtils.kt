package edu.yuferov.shop.util

import android.view.View
import android.widget.TextView
import edu.yuferov.shop.R
import edu.yuferov.shop.domain.IHasPrice
import edu.yuferov.shop.domain.Price

fun TextView.formatPrice(price: Price) {
    text = context.getString(R.string.template_price, price.value)
}

fun bindPrice(price: TextView, discountPrice: TextView, model: IHasPrice) {
    val context = price.context
    if (model.hasDiscount) {
        price.foreground = context.getDrawable(R.drawable.cross_out)
        discountPrice.visibility = View.VISIBLE
        discountPrice.formatPrice(model.totalPrice)
    } else {
        price.foreground = null
        discountPrice.text = ""
        discountPrice.visibility = View.GONE
    }
    price.formatPrice(model.price)
}
