package edu.yuferov.shop.domain

interface IHasPrice {
    val hasDiscount: Boolean
        get() = discount > 0.0

    val totalPrice
        get() = price - discount

    val discountValue
        get() = price * discount

    val discount: Percent
    val price: Price
}