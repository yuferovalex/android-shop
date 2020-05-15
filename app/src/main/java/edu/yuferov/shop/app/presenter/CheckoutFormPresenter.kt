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
    private val api: MainApi,
    private val mainPresenter: MainPresenter
) : BasePresenter<ICheckoutFormView>() {

    private lateinit var cart: Cart
    private lateinit var userInfo: UserInfo

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
        val msg = validateName(userInfo.lastName)
        viewState.setLastNameError(msg)
        onUserInfoChanged()
    }

    fun onFirstNameChanged(value: String) {
        userInfo.firstName = value.trim()
        val msg = validateName(userInfo.firstName)
        viewState.setFirstNameError(msg)
        onUserInfoChanged()
    }

    fun onMiddleNameChanged(value: String) {
        userInfo.middleName = value.trim()
        val msg = validateName(userInfo.middleName, required = false)
        viewState.setMiddleNameError(msg)
        onUserInfoChanged()
    }

    fun onPhoneChanged(value: String) {
        userInfo.phone = value.trim()
        val msg = validatePhone(userInfo.phone)
        viewState.setPhoneError(msg)
        onUserInfoChanged()
    }

    fun onPaymentTypeChanged(value: PaymentType) {
        userInfo.paymentType = value
        onUserInfoChanged()
    }

    private fun onUserInfoChanged() {
        presenterScope.launch { userInfoRepository.save(userInfo) }
    }

    fun onSubmitBtnClicked() {
        if (!validateAll()) {
            return
        }
        val request = OrderRequest(cart, userInfo)
        makeRequest {
            val number = api.createOrder(request)
            cartRepository.clear()
            mainPresenter.updateItemsCount()
            viewState.navigateToSuccess(number)
        }
    }

    private fun validateAll(): Boolean {
        var msg = validateName(userInfo.lastName)
        var result = msg == 0
        viewState.setLastNameError(msg)

        msg = validateName(userInfo.firstName)
        result = result && msg == 0
        viewState.setFirstNameError(msg)

        msg = validateName(userInfo.middleName, required = false)
        result = result && msg == 0
        viewState.setMiddleNameError(msg)

        msg = validatePhone(userInfo.phone)
        result = result && msg == 0
        viewState.setPhoneError(msg)

        return result
    }

    private fun validatePhone(phone: String): Int {
        val phoneNumberCount = phone.count { !" +-".contains(it) }
        if (phoneNumberCount != 11) {
            return R.string.phone_must_contains_eleven_numbers
        }

        return 0
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