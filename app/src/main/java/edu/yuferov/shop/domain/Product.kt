package edu.yuferov.shop.domain

import android.net.Uri

data class Product (
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Uri,
    val image: Uri,
    override val price: Price,
    override val discount: Percent,
    val attributes: List<Attribute>
) : IHasPrice {

    data class Attribute(
        val name: String,
        val value: String
    )
}