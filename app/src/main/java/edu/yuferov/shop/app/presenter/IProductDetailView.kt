package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Product
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IProductDetailView : IBaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun bind(product: Product)

}
