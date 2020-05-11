package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Product
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IProductListView : MvpView {
    @StateStrategyType(SingleStateStrategy::class)
    fun showLoadingStatus()

    @StateStrategyType(SingleStateStrategy::class)
    fun showLoadErrorStatus()

    @StateStrategyType(SingleStateStrategy::class)
    fun bind(data: List<Product>)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToProductDetails(productId: Int)
}