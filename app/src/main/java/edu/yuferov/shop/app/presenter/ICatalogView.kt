package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Category
import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(SingleStateStrategy::class)
interface ICatalogView : MvpView {
    fun bind(categories: List<Category>)
    fun showLoadingStatus()
    fun showLoadErrorStatus()

    @StateStrategyType(SkipStrategy::class)
    fun navigateToCategory(categoryId: Int)
}
