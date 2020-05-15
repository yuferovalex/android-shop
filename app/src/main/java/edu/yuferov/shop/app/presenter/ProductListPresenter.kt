package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.domain.Product
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class ProductListPresenter(
    private val api: MainApi,
    private val repository: CartRepository,
    private val mainPresenter: MainPresenter,
    private val categoryId: Int
) : BasePresenter<IProductListView>() {

    class Fabric @Inject constructor(
        private val api: MainApi,
        private val mainPresenter: MainPresenter,
        private val repository: CartRepository
    ) {
        fun create(categoryId: Int) = ProductListPresenter(api, repository, mainPresenter, categoryId)
    }

    companion object {
        const val PAGE_SIZE = 15
    }

    private lateinit var pageData: MutableList<Product>

    init {
        makeRequest {
            pageData = api.loadProductsOfCategory(categoryId, 0, PAGE_SIZE)
            viewState.bind(pageData)
        }
    }

    fun onAddToCardClicked(product: Product) = presenterScope.launch {
        repository.add(product.id, 1)
        mainPresenter.updateItemsCount()
    }

    fun onProductDetailsRequested(product: Product) =
        viewState.navigateToProductDetails(product.id)

    fun onLoadNextPage(page: Int) = presenterScope.launch {
        val currentCount = pageData.size
        val nextPage = api.loadProductsOfCategory(categoryId, page, PAGE_SIZE)
        val received = nextPage.size
        pageData.addAll(nextPage)
        viewState.itemsAppended(currentCount, received)
    }
}
