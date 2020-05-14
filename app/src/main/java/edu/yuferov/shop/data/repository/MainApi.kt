package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.BuyRequest
import edu.yuferov.shop.domain.Category
import edu.yuferov.shop.domain.Product

interface MainApi {
    suspend fun loadCategories(): List<Category>
    suspend fun loadProductsOfCategory(categoryId: Int): List<Product>
    suspend fun loadProduct(productId: Int): Product
    suspend fun buyRequest(request: BuyRequest): Int
}