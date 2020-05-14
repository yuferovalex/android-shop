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
    private val repository: CartRepository
) : BasePresenter<ICartView>() {

    private lateinit var cart: Cart

    init {
        makeRequest {
            cart = repository.load()
            viewState.setItems(cart)
            updateButton()
        }
    }

    fun onCheckoutBtnClicked() {
        viewState.navigateToCheckout()
    }

    fun onItemClicked(cartItem: CartItem) {
        viewState.navigateToProduct(cartItem.product.id)
    }

    fun onRemoveItemClicked(cartItem: CartItem) {
        val index = cart.items.indexOf(cartItem)
        cart.items.remove(cartItem)
        viewState.removeItem(index)
        updateButton()
        viewState.showItemRemovedMessage(index, cartItem)

        presenterScope.launch { repository.save(cart) }
    }

    fun onRestoreItemClicked(index: Int, cartItem: CartItem) {
        cart.items.add(index, cartItem)
        viewState.insertItem(index)
        updateButton()

        presenterScope.launch { repository.save(cart) }
    }

    private fun updateButton() {
        viewState.updateCheckoutBtnState(cart.items.isNotEmpty(), cart.totalPrice)
    }

    fun onQuantityChanged(cartItem: CartItem, value: Int) {
        cartItem.quantity = value
        presenterScope.launch {
            repository.setCount(cartItem.product.id, value)
        }
    }

    fun onQuantityDialogOkClicked(cartItem: CartItem) {
        viewState.hideQuantityDialog()
        val index = cart.items.indexOf(cartItem)
        viewState.updateItem(index)
        updateButton()
    }

    fun onQuantityDialogCancelClicked(cartItem: CartItem, initialQuantity: Int) {
        cartItem.quantity = initialQuantity
        presenterScope.launch {
            repository.setCount(cartItem.product.id, initialQuantity)
        }
        val index = cart.items.indexOf(cartItem)
        viewState.updateItem(index)
        updateButton()
        viewState.hideQuantityDialog()
    }

    fun onQuantityClick(cartItem: CartItem) {
        viewState.showQuantityDialog(cartItem, cartItem.quantity)
    }
}