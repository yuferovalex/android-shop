package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Product
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface IProductDetailView : MvpView, IHasNetworkStatusView {
    @StateStrategyType(SingleStateStrategy::class)
    fun bind(product: Product)
}
