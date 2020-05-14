package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.R
import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.data.repository.UserInfoRepository
import edu.yuferov.shop.domain.OrderRequest
import edu.yuferov.shop.domain.Cart
import edu.yuferov.shop.domain.PaymentType
import edu.yuferov.shop.domain.UserInfo
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class CheckoutFormPresenter @Inject constructor(
    private val cartRepository: CartRepository,
    private val userInfoRepository: UserInfoRepository,
    private val api: MainApi
) : BasePresenter<ICheckoutFormView>() {

    lateinit var cart: Cart
    lateinit var userInfo: UserInfo

    init {
        makeRequest {
            cart = cartRepository.load()
            userInfo = userInfoRepository.load()
            viewState.setPrices(cart)
            viewState.setUserInfo(userInfo)
        }
    }

    fun onLastNameChanged(value: String) {
        userInfo.lastName = value.trim()
        onUserInfoChanged()
    }

    fun onFirstNameChanged(value: String) {
        userInfo.firstName = value.trim()
        onUserInfoChanged()
    }

    fun onMiddleNameChanged(value: String) {
        userInfo.middleName = value.trim()
        onUserInfoChanged()
    }

    fun onPhoneChanged(value: String) {
        userInfo.phone = value.trim()
        onUserInfoChanged()
    }

    fun onPaymentTypeChanged(value: PaymentType) {
        userInfo.paymentType = value
        onUserInfoChanged()
    }

    private fun onUserInfoChanged() {
        validate()
        presenterScope.launch { userInfoRepository.save(userInfo) }
    }

    fun onSubmitBtnClicked() {
        if (!validate()) {
            return
        }
        val request = OrderRequest(cart, userInfo)
        presenterScope.launch {
            val number = api.createOrder(request)
            cartRepository.clear()
            viewState.navigateToSuccess(number)
        }
    }

    private fun validate(): Boolean {
        var msg = validateName(userInfo.lastName)
        var result = msg == 0
        viewState.setLastNameError(msg)

        msg = validateName(userInfo.firstName)
        result = result && msg == 0
        viewState.setFirstNameError(msg)

        msg = validateName(userInfo.middleName, required = false)
        result = result && msg == 0
        viewState.setMiddleNameError(msg)

        return result
    }

    private fun validateName(name: String, required: Boolean = true): Int {
        if (required && name.isBlank()) {
            return R.string.field_can_not_be_empty_error
        }
        if (name.contains(Regex("[^\\p{L}]"))) {
            return R.string.field_contains_invalid_characters_error
        }
        return 0
    }



}