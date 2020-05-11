package edu.yuferov.shop.domain

import android.net.Uri

data class Category (
    val id: Int,
    var name: String,
    var icon: Uri
) {
}
