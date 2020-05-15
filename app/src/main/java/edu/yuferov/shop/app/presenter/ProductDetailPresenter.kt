package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.domain.Product
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class ProductDetailPresenter(
    private val api: MainApi,
    private val repository: CartRepository,
    private val mainPresenter: MainPresenter,
    productId: Int
) : BasePresenter<IProductDetailView>() {

    class Fabric @Inject constructor(
        private val api: MainApi,
        private val repository: CartRepository,
        private val mainPresenter: MainPresenter
    ) {
        fun create(productId: Int) = ProductDetailPresenter(api, repository, mainPresenter, productId)
    }

    lateinit var product: Product

    init {
        makeRequest {
            product = api.loadProduct(productId)
            viewState.bind(product)
        }
    }

    fun onAddToCartClicked() = presenterScope.launch {
        repository.add(product.id, 1)
        mainPresenter.updateItemsCount()
    }

    fun onShareButtonClicked() {
        mainPresenter.showProductShareDialog(product.id)
    }
}
