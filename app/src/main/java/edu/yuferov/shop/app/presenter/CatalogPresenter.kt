package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.data.repository.MainApi
import edu.yuferov.shop.domain.Category
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class CatalogPresenter @Inject constructor(
    private val repository: MainApi
) : BasePresenter<ICatalogView>() {
    init {
        makeRequest {
            val categories = repository.loadCategories()
            viewState.bind(categories)
        }
    }

    fun categoryClicked(category: Category) {
        viewState.navigateToCategory(category.id)
    }
}
