package edu.yuferov.shop.app.presenter

import android.util.Log
import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.domain.Product
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class ProductDetailPresenter(
    private val api: MainApi,
    private val repository: CartRepository,
    productId: Int
) : MvpPresenter<IProductDetailView>() {

    class Fabric @Inject constructor(
        private val api: MainApi,
        private val repository: CartRepository
    ) {
        fun create(productId: Int) = ProductDetailPresenter(api, repository, productId)
    }

    lateinit var product: Product

    init {
        loadData(productId)
    }

    private fun loadData(productId: Int) = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            product = api.loadProduct(productId)
            viewState.bind(product)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while loading product page: ${throwable.message}"
            )
            viewState.showLoadErrorStatus()
        }
    }

    fun onAddToCartClicked() = presenterScope.launch {
        repository.add(product.id, 1)
    }
}