package edu.yuferov.shop.data.repository

import android.net.Uri
import edu.yuferov.shop.domain.Category
import edu.yuferov.shop.domain.Percent
import edu.yuferov.shop.domain.Price
import edu.yuferov.shop.domain.Product
import kotlinx.coroutines.delay
import java.util.stream.Collectors
import java.util.stream.IntStream

class MainRepositoryImpl : MainApi {

    val img =
        Uri.parse("https://w0.pngwave.com/png/155/611/artist-s-book-scalable-graphics-gray-books-s-png-clip-art-thumbnail.png")

    override suspend fun loadCategories(): List<Category> {
        delay(1_000)
        return listOf(
            Category(
                id = 1,
                name = "Детектив",
                icon = Uri.parse("https://cdn3.iconfinder.com/data/icons/caps-hats/512/Mans_Cap-512.png")
            ),
            Category(
                id = 2,
                name = "Драма",
                icon = Uri.parse("https://cdn2.iconfinder.com/data/icons/life-concepts-lifestyles/128/entertainment-2-512.png")
            ),
            Category(
                id = 3,
                name = "Комедия",
                icon = Uri.parse("https://cdn2.iconfinder.com/data/icons/life-concepts-lifestyles/128/entertainment-2-512.png")
            )
        )
    }

    override suspend fun loadProductsOfCategory(categoryId: Int): List<Product> {
        delay(1_000)
        return IntStream.range(1, 20)
            //.skip((index * length).toLong())
            //.limit(length.toLong())
            .mapToObj { createProduct(it) }
            .collect(Collectors.toList())
    }

    override suspend fun loadProduct(productId: Int): Product {
        delay(1_000)
        return createProduct(productId)
    }

    private fun createProduct(productId: Int) = Product(
        id = productId,
        title = "Продукт $productId",
        thumbnail = img,
        discount = Percent((productId * 5 % 20).toDouble()),
        image = img,
        price = Price(100.00),
        description = "Описание продукта $productId. LLorem ipsum dolor sit amet, consectetur " +
                "adipiscing elit. Cras eros metus, varius eu pellentesque eget, finibus et erat. " +
                "Mauris id dolor eros. Sed varius facilisis dolor in feugiat. Donec non est quis " +
                "leo malesuada imperdiet. Phasellus lobortis, justo quis iaculis consectetur, " +
                "nunc purus faucibus nunc, sit amet cursus ex eros vitae justo.",
        attributes = listOf(
            Product.Attribute("Атрибут 1", "Значение 1"),
            Product.Attribute("Атрибут 2", "Значение 2"),
            Product.Attribute("Атрибут 3", "Значение 3"),
            Product.Attribute("Атрибут 4", "Значение 4")
        )
    )
}