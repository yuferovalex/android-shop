package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.OrderRequest
import edu.yuferov.shop.domain.Category
import edu.yuferov.shop.domain.Product
import retrofit2.http.*

interface MainApi {
    @GET("category")
    suspend fun loadCategories(): List<Category>

    @GET("category/{categoryId}")
    suspend fun loadProductsOfCategory(
        @Path("categoryId") categoryId: Int,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): MutableList<Product>

    @GET("product/{productId}")
    suspend fun loadProduct(@Path("productId") productId: Int): Product

    @POST("order")
    suspend fun createOrder(@Body request: OrderRequest): Int
}