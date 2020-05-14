package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.IHasPrice
import edu.yuferov.shop.domain.UserInfo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ICheckoutFormView : MvpView, IHasNetworkStatusView {
    @StateStrategyType(SingleStateStrategy::class)
    fun hideNetworkStatus()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setUserInfo(userInfo: UserInfo)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setLastNameError(msgId: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setFirstNameError(msgId: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setMiddleNameError(msgId: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPhoneError(msgId: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setPrices(model: IHasPrice)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToSuccess(orderNumber: Int)
}