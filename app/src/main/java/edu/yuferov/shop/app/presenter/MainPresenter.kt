package edu.yuferov.shop.app.presenter

import android.util.Log
import edu.yuferov.shop.R
import edu.yuferov.shop.data.repository.CartRepository
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject
import javax.inject.Singleton

@InjectViewState
class MainPresenter @Inject constructor(
    private val repository: CartRepository
) : MvpPresenter<IMainView>() {

    init {
        updateItemsCount()
    }

    fun updateItemsCount() = presenterScope.launch {
        try {
            val cart = repository.load()
            viewState.setCartItemCount(cart.totalItemsCount)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred: ${throwable.message}"
            )
            viewState.setCartItemCount(0)
        }
    }

    fun onDestinationChanged(destination: Int) {
        when (destination) {
            R.id.checkout_form_fragment -> viewState.setBottomBarVisible(false)
            R.id.checkout_success_fragment -> viewState.setBottomBarVisible(false)
            else -> viewState.setBottomBarVisible(true)
        }
    }



}
