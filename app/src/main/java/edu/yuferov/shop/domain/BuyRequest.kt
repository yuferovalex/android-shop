package edu.yuferov.shop.domain

class BuyRequest(
    cart: Cart
){
    data class CartItem(val productId: Int, val count: Int)

    val cart: List<CartItem> = cart.items.map {
        CartItem(it.product.id, it.quantity)
    }

    val totalPrice: Double = cart.totalPrice.value

    var lastName: String = ""
    var firstName: String = ""
    var middleName: String = ""
    var phone: String = ""
    var paymentType: String = ""
}