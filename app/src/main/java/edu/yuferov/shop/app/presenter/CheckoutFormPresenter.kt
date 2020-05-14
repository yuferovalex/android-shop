package edu.yuferov.shop.app.presenter

import android.util.Log
import edu.yuferov.shop.R
import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.domain.BuyRequest
import edu.yuferov.shop.domain.Cart
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class CheckoutFormPresenter @Inject constructor(
    private val repository: CartRepository
) : MvpPresenter<ICheckoutFormView>() {

    enum class PaymentType {
        CASH, CARD
    }

    lateinit var cart: Cart
    lateinit var request: BuyRequest

    init {
        loadData()
    }

    private fun loadData() = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            cart = repository.load()
            request = BuyRequest(cart)
            viewState.setPrices(cart)
            viewState.hideNetworkStatus()
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while downloading cart: ${throwable.message}"
            );
            viewState.showLoadErrorStatus()
        }
    }

    fun onLastNameChanged(value: String) {
        request.lastName = value
        validate()
    }

    fun onFirstNameChanged(value: String) {
        request.firstName = value
        validate()
    }

    fun onMiddleNameChanged(value: String) {
        request.middleName = value
        validate()
    }

    fun onPhoneChanged(value: String) {
        request.phone = value
        validate()
    }

    fun onPaymentTypeChanged(value: PaymentType) {
        request.paymentType = value.toString()
    }

    fun onSubmitBtnClicked() {
        if (!validate()) {
            return
        }
        // TODO: PUSH /api/buy
    }

    private fun validate(): Boolean {
        var msg = validateName(request.lastName)
        var result = msg == 0
        viewState.setLastNameError(msg)

        msg = validateName(request.lastName)
        result = result && msg == 0
        viewState.setFirstNameError(msg)

        msg = validateName(request.lastName, required = false)
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