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
class ProductListPresenter(
    private val repository: MainApi,
    private val addToCard: AddToCartUseCase,
    private val categoryId: Int
) : MvpPresenter<IProductListView>() {

    init {
        loadData()
    }

    class Fabric @Inject constructor(
        private val repository: MainApi,
        private val addToCard: AddToCartUseCase
    ) {
        fun create(categoryId: Int) = ProductListPresenter(repository, addToCard, categoryId)
    }

    private fun loadData() = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            val pageData = repository.loadProductsOfCategory(categoryId)
            viewState.bind(pageData)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while loading product page: ${throwable.message}"
            )
            viewState.showLoadErrorStatus()
        }
    }

    fun onAddToCardClicked(product: Product) = addToCard(product)

    fun onProductDetailsRequested(product: Product) =
        viewState.navigateToProductDetails(product.id)
}