package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.domain.Cart
import edu.yuferov.shop.domain.CartItem
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class CartPresenter @Inject constructor(
    private val repository: CartRepository,
    private val mainPresenter: MainPresenter
) : BasePresenter<ICartView>() {

    private lateinit var cart: Cart

    init {
        makeRequest {
            cart = repository.load()
            viewState.setItems(cart)
            updateButtonAndEmptyMessage()
        }
    }

    fun onCheckoutBtnClicked() {
        viewState.navigateToCheckout()
    }

    fun onItemClicked(cartItem: CartItem) {
        viewState.navigateToProduct(cartItem.product.id)
    }

    fun onRemoveItemClicked(cartItem: CartItem) = presenterScope.launch {
        val index = cart.items.indexOf(cartItem)
        cart.items.remove(cartItem)
        repository.save(cart)

        viewState.removeItem(index)
        updateButtonAndEmptyMessage()
        viewState.showItemRemovedMessage(index, cartItem)
        mainPresenter.updateItemsCount()
    }

    fun onRestoreItemClicked(index: Int, cartItem: CartItem) = presenterScope.launch {
        cart.items.add(index, cartItem)
        repository.save(cart)

        viewState.insertItem(index)
        updateButtonAndEmptyMessage()
        mainPresenter.updateItemsCount()
    }

    private fun updateButtonAndEmptyMessage() {
        viewState.updateCheckoutBtnState(cart.items.isNotEmpty(), cart.totalPrice)
        viewState.emptyCartMessageVisibility(cart.items.isEmpty())
    }

    fun onQuantityChanged(cartItem: CartItem, value: Int) = presenterScope.launch {
        cartItem.quantity = value
        repository.setCount(cartItem.product.id, value)
    }

    fun onQuantityDialogOkClicked(cartItem: CartItem) {
        viewState.hideQuantityDialog()
        val index = cart.items.indexOf(cartItem)
        viewState.updateItem(index)
        updateButtonAndEmptyMessage()
        mainPresenter.updateItemsCount()
    }

    fun onQuantityDialogCancelClicked(cartItem: CartItem, initialQuantity: Int) = presenterScope.launch {
        cartItem.quantity = initialQuantity
        repository.setCount(cartItem.product.id, initialQuantity)
        mainPresenter.updateItemsCount()
        val index = cart.items.indexOf(cartItem)
        viewState.updateItem(index)
        updateButtonAndEmptyMessage()
        viewState.hideQuantityDialog()
        mainPresenter.updateItemsCount()
    }

    fun onQuantityClick(cartItem: CartItem) {
        viewState.showQuantityDialog(cartItem, cartItem.quantity)
    }
}