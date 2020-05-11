package edu.yuferov.shop.app.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class CheckoutFormPresenter @Inject constructor() : MvpPresenter<ICheckoutFormView>() {
}