package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.Cart

interface CartRepository {

    suspend fun save(cart: Cart)

    suspend fun load(): Cart

    suspend fun add(productId: Int, count: Int)

    suspend fun setCount(productId: Int, count: Int)

    suspend fun clear()

}