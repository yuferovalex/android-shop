package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.OrderRequest
import edu.yuferov.shop.domain.Category
import edu.yuferov.shop.domain.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainApi {
    @GET("category")
    suspend fun loadCategories(): List<Category>

    @GET("category/{categoryId}")
    suspend fun loadProductsOfCategory(@Path("categoryId") categoryId: Int): List<Product>

    @GET("product/{productId}")
    suspend fun loadProduct(@Path("productId") productId: Int): Product

    @POST("order")
    suspend fun createOrder(@Body request: OrderRequest): Int
}