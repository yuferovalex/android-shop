package edu.yuferov.shop.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import edu.yuferov.shop.R

fun ImageView.loadImage(url: Uri) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_broken_image)
        .into(this);
}
