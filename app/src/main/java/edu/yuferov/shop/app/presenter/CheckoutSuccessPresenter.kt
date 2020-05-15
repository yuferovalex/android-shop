package edu.yuferov.shop.app.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class CheckoutSuccessPresenter(
    private val orderNumber: Int
) : MvpPresenter<ICheckoutSuccessView>()  {

    class Fabric @Inject constructor() {
        fun create(orderNumber: Int) = CheckoutSuccessPresenter(orderNumber)
    }

    init {
        viewState.setOrderNumber(orderNumber)
    }
}
