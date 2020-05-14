package edu.yuferov.shop.domain

data class UserInfo(
    var lastName: String,
    var firstName: String,
    var middleName: String,
    var phone: String,
    var paymentType: PaymentType
) {
}