package edu.yuferov.shop.app.usecase

import javax.inject.Inject

class IncreaseCartItemCountUseCase @Inject constructor(

) {
    @Suppress("UNUSED_PARAMETER")
    suspend operator fun invoke(productId: Int) {
        // TODO: Implement me
        // cart.items.find { it.id === productId }?.count += 1
        // repo.increaseItemsCount(productId, newCount)
    }
}