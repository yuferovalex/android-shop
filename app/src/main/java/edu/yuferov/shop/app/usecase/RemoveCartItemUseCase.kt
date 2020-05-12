package edu.yuferov.shop.app.usecase

import javax.inject.Inject

class RemoveCartItemUseCase @Inject constructor(

) {
    @Suppress("UNUSED_PARAMETER")
    suspend operator fun invoke(productId: Int) {
        // TODO: Implement me
        // cart.items.remove { it.id === productId }
        // repo.remove(productId)
    }
}