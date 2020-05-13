package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Product
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface IProductListView : MvpView, IHasNetworkStatusView {
    @StateStrategyType(SingleStateStrategy::class)
    fun bind(data: List<Product>)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToProductDetails(productId: Int)
}