package edu.yuferov.shop.domain

import android.net.Uri

data class Product (
    var id: Int,
    var title: String,
    var description: String,
    var thumbnail: Uri,
    var image: Uri,
    var price: Price,
    var discount: Percent,
    var attributes: List<Attribute>
) {
    data class Attribute(
        var name: String,
        var value: String
    )

    val hasDiscount
        get() = discount > 0.0

    val totalPrice: Price
        get() = price - discount
}