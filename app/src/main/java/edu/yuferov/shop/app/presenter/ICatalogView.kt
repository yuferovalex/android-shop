package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Category
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ICatalogView : IBaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun bind(categories: List<Category>)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToCategory(categoryId: Int)
}
