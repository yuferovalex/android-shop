package edu.yuferov.shop.app.presenter

import edu.yuferov.shop.domain.Cart
import edu.yuferov.shop.domain.CartItem
import edu.yuferov.shop.domain.Price
import moxy.MvpView
import moxy.viewstate.strategy.*

interface ICartView : IBaseView {
    companion object {
        const val QUANTITY_DIALOG_TAG = "QUANTITY_DIALOG_TAG"
    }

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setItems(cart: Cart)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateCheckoutBtnState(active: Boolean, price: Price)

    @StateStrategyType(SkipStrategy::class)
    fun removeItem(index: Int)

    @StateStrategyType(SkipStrategy::class)
    fun insertItem(index: Int)

    @StateStrategyType(SkipStrategy::class)
    fun updateItem(index: Int)

    @StateStrategyType(SkipStrategy::class)
    fun navigateToCheckout()

    @StateStrategyType(SkipStrategy::class)
    fun navigateToProduct(id: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showItemRemovedMessage(index: Int, cartItem: CartItem)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = QUANTITY_DIALOG_TAG)
    fun showQuantityDialog(cartItem: CartItem, initialQuantity: Int)

    @StateStrategyType(AddToEndSingleTagStrategy::class, tag = QUANTITY_DIALOG_TAG)
    fun hideQuantityDialog()
}