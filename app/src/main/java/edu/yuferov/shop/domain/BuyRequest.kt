package edu.yuferov.shop.domain

class BuyRequest(
    cart: Cart,
    userInfo: UserInfo
){
    data class CartItem(val productId: Int, val count: Int)

    val cart: List<CartItem> = cart.items.map {
        CartItem(it.product.id, it.quantity)
    }

    val totalPrice: Double = cart.totalPrice.value
    val lastName: String = userInfo.lastName
    val firstName: String = userInfo.firstName
    val middleName: String = userInfo.middleName
    val phone: String = userInfo.phone
    val paymentType: String = userInfo.paymentType.toString()
}