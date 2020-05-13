package edu.yuferov.shop.app.presenter

import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface IHasNetworkStatusView {
    fun showLoadingStatus()
    fun showLoadErrorStatus()
}