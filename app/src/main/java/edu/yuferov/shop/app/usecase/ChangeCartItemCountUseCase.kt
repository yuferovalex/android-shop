package edu.yuferov.shop.app.usecase

import edu.yuferov.shop.data.repository.CartRepository
import javax.inject.Inject

class ChangeCartItemCountUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(productId: Int, newCount: Int) {
        repository.setCount(productId, newCount)
    }
}