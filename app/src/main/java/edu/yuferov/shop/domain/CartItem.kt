package edu.yuferov.shop.domain

class CartItem(
    val product: Product,
    var quantity: Int
) : IHasPrice {

    val thumbnail
        get() = product.thumbnail

    val title
        get() = product.title

    val priceForOne
        get() = product.totalPrice

    override val discount: Percent
        get() = product.discount

    override val price: Price
        get() = product.price * quantity
}