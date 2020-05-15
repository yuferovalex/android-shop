package edu.yuferov.shop.app.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ICheckoutSuccessView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setOrderNumber(orderNumber: Int)
}
