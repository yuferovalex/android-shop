package edu.yuferov.shop.app.usecase

import javax.inject.Inject

class ChangeCartItemCountUseCase @Inject constructor(

) {
    @Suppress("UNUSED_PARAMETER")
    suspend operator fun invoke(productId: Int, newCount: Int) {
        // TODO: Implement me
        // cart.items.find { it.id === productId }?.count = newCount
        // repo.changeCount(productId, newCount)
    }
}