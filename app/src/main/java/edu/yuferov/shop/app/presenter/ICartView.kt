package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Cart
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface ICartView : MvpView, IHasNetworkStatusView {
    fun bind(cart: Cart)
}