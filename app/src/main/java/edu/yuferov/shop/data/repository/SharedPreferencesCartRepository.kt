package edu.yuferov.shop.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import edu.yuferov.shop.domain.Cart
import edu.yuferov.shop.domain.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject

class SharedPreferencesCartRepository @Inject constructor(
    private val store: SharedPreferences,
    private val api: MainApi
) : CartRepository {

    override suspend fun save(cart: Cart) {
        val serialized = cart.items
            .map { Tuple(it.product.id, it.quantity).toString() }
            .joinToString(ITEMS_DELIMITER)

        store.edit {
            putString(CART_TAG, serialized)
        }
    }

    override suspend fun load(): Cart {
        val items = store.getString(CART_TAG, null).orEmpty()
            .split(ITEMS_DELIMITER)
            .filter { it.isNotEmpty() }
            .map { Tuple.of(it) }
            .map {
                GlobalScope.async(Dispatchers.IO) {
                    CartItem(api.loadProduct(it.id), it.count)
                }
            }
            .awaitAll()
            .toMutableList()

        return Cart(items)
    }

    override fun add(productId: Int, count: Int) {
        val items = loadItems()
        items.add(Tuple(productId, count))
        save(items)
    }

    override fun setCount(productId: Int, count: Int) {
        val items = loadItems()
        items.find { it.id == productId }?.count = count
        save(items)
    }

    override fun increaseCount(productId: Int) {
        val items = loadItems()
        items.find { it.id == productId }
            ?.count
            ?.inc()
        save(items)
    }

    override fun remove(productId: Int) {
        val items = loadItems()
        items.removeIf { it.id == productId }
        save(items)
    }

    private class Tuple(var id: Int, var count: Int) {
        companion object {
            fun of(string: String): Tuple {
                val items = string.split(PARAMS_DELIMITER)
                return Tuple(items[0].toInt(), items[1].toInt())
            }
        }

        override fun toString(): String {
            return "$id$PARAMS_DELIMITER$count"
        }
    }

    private fun loadItems() = store.getString(CART_TAG, null).orEmpty()
        .split(ITEMS_DELIMITER)
        .filter { it.isNotEmpty() }
        .map { Tuple.of(it) }
        .toMutableList()

    private fun save(items: List<Tuple>) {
        val serialized = items.map { it.toString() }
            .joinToString(ITEMS_DELIMITER)
        store.edit {
            putString(CART_TAG, serialized)
        }
    }

    companion object {
        private const val CART_TAG = "CART_TAG"
        private const val ITEMS_DELIMITER = ";"
        private const val PARAMS_DELIMITER = ":"
    }
}