package edu.yuferov.shop.app.presenter

import android.util.Log
import edu.yuferov.shop.app.usecase.AddToCartUseCase
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.domain.Product
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class ProductDetailPresenter(
    private val repository: MainApi,
    private val addToCart: AddToCartUseCase,
    productId: Int
) : MvpPresenter<IProductDetailView>() {

    class Fabric @Inject constructor(
        private val repository: MainApi,
        private val addToCart: AddToCartUseCase
    ) {
        fun create(productId: Int) = ProductDetailPresenter(repository, addToCart, productId)
    }

    lateinit var product: Product

    init {
        loadData(productId)
    }

    private fun loadData(productId: Int) = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            product = repository.loadProduct(productId)
            viewState.bind(product)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while loading product page: ${throwable.message}"
            )
            viewState.showLoadErrorStatus()
        }
    }

    fun onAddToCartClicked() = addToCart(product)
}