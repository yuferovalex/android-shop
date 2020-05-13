package edu.yuferov.shop.app.usecase

import edu.yuferov.shop.data.repository.CartRepository
import edu.yuferov.shop.domain.Product
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    operator fun invoke(product: Product) {
        repository.add(product.id, 1)
    }
}