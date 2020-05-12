package edu.yuferov.shop.app.usecase

import edu.yuferov.shop.domain.Product
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(

) {
    suspend operator fun invoke(@Suppress("UNUSED_PARAMETER") product: Product) {
        // TODO: Implement me
        // cart.items.add(product)
        // repository.add(product.id, 1)
    }
}