package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.Category
import edu.yuferov.shop.domain.Product

interface IMainRepository {
    suspend fun loadCategories(): List<Category>
    suspend fun loadProductsOfCategory(categoryId: Int): List<Product>
    suspend fun loadProduct(productId: Int): Product
}