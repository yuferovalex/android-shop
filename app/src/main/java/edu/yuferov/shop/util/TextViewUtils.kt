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
    discountPrice.formatPrice(model.totalPrice)
    if (model.hasDiscount) {
        price.visibility = View.VISIBLE
        price.formatPrice(model.price)
    } else {
        price.visibility = View.GONE
    }
}
