package edu.yuferov.shop.data.repository

import edu.yuferov.shop.domain.Cart

interface CartRepository {

    suspend fun save(cart: Cart)

    suspend fun load(): Cart

    fun add(productId: Int, count: Int)

    fun setCount(productId: Int, count: Int)

    fun increaseCount(productId: Int)

    fun remove(productId: Int)


}