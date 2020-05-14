package edu.yuferov.shop.domain

class Cart(
    val items: MutableList<CartItem>
) : IHasPrice {

    override val totalPrice: Price
        get() = items.fold(Price(0.0)) { acc, cartItem ->
            acc + cartItem.totalPrice
        }

    override val price: Price
        get() = items.fold(Price(0.0)) { acc, cartItem ->
            acc + cartItem.price
        }

    override val discount: Percent
        get() = Percent(
            if (price.value > 0.0) {
                (price.value - totalPrice.value) / price.value * 100
            } else {
                0.0
            }
        )

}

