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
class ProductListPresenter(
    private val api: MainApi,
    private val repository: CartRepository,
    private val categoryId: Int
) : MvpPresenter<IProductListView>() {

    init {
        loadData()
    }

    class Fabric @Inject constructor(
        private val api: MainApi,
        private val repository: CartRepository
    ) {
        fun create(categoryId: Int) = ProductListPresenter(api, repository, categoryId)
    }

    private fun loadData() = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            val pageData = api.loadProductsOfCategory(categoryId)
            viewState.bind(pageData)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while loading product page: ${throwable.message}"
            )
            viewState.showLoadErrorStatus()
        }
    }

    fun onAddToCardClicked(product: Product) = presenterScope.launch {
        repository.add(product.id, 1)
    }

    fun onProductDetailsRequested(product: Product) =
        viewState.navigateToProductDetails(product.id)
}