package edu.yuferov.shop.app.presenter

import android.util.Log
import edu.yuferov.shop.data.repository.IMainRepository
import edu.yuferov.shop.domain.Category
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class CatalogPresenter @Inject constructor(
    private val repository: IMainRepository
) : MvpPresenter<ICatalogView>() {

    init {
        downloadCategories()
    }

    fun categoryClicked(category: Category) {
        viewState.navigateToCategory(category.id)
    }

    private fun downloadCategories() = presenterScope.launch {
        try {
            viewState.showLoadingStatus()
            val categories = repository.loadCategories()
            viewState.bind(categories)
        } catch (throwable: Throwable) {
            Log.e(
                "Network error",
                "An error occurred while downloading categories: ${throwable.message}"
            );
            viewState.showLoadErrorStatus()
        }
    }
}