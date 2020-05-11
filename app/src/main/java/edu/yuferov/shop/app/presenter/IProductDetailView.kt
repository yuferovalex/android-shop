package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Product
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IProductDetailView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun showLoadingStatus()

    @StateStrategyType(SingleStateStrategy::class)
    fun showLoadErrorStatus()

    @StateStrategyType(SingleStateStrategy::class)
    fun bind(product: Product)
}
