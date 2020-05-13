package edu.yuferov.shop.app.presenter

import android.util.Log
import edu.yuferov.shop.data.repository.CartRepository
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class CartPresenter @Inject constructor(
    private val repository: CartRepository
): MvpPresenter<ICartView>() {

    init {
        loadCart()
    }

    private fun loadCart() = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            val cart = repository.load()
            viewState.bind(cart)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while fetching cart items: ${throwable.message}"
            );
            viewState.showLoadErrorStatus()
        }
    }

}