package edu.yuferov.shop.app.presenter

import android.util.Log
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope

abstract class BasePresenter <T : IBaseView> : MvpPresenter<T>() {

    protected fun makeRequest(load: suspend () -> Unit) = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            load()
            viewState.hideLoadingStatus()
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred: ${throwable.message}"
            )
            viewState.showLoadErrorStatus()
        }
    }

}