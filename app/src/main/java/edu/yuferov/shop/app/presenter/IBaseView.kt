package edu.yuferov.shop.app.presenter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IBaseView : MvpView {

    companion object {
        const val LOADING_STATUS_TAG = "LOADING_STATUS_TAG"
    }

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = LOADING_STATUS_TAG)
    fun showLoadingStatus()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = LOADING_STATUS_TAG)
    fun hideLoadingStatus()

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = LOADING_STATUS_TAG)
    fun showLoadErrorStatus()


}