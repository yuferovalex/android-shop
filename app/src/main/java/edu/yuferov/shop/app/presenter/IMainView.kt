package edu.yuferov.shop.app.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEnd

interface IMainView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setCartItemCount(count: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setBottomBarVisible(visible: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun showShareProduct(productId: Int)
}