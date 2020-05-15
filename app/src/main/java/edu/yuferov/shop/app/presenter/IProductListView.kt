package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Product
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IProductListView : IBaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun bind(data: List<Product>)

    @StateStrategyType(SkipStrategy::class)
    fun itemsAppended(from: Int, count: Int)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToProductDetails(productId: Int)
}