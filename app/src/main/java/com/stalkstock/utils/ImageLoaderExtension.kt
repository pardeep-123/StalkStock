package com.stalkstock.utils


import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stalkstock.R
import com.stalkstock.utils.others.GlobalVariables.URL.IMAGE_URL

fun ImageView.loadImage(categoryImg: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    if (categoryImg.contains(IMAGE_URL)) {
        Glide.with(this)
                .load(categoryImg)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.place_holder)
                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)

    } else {
        Glide.with(this)
                .load("$IMAGE_URL$categoryImg")
                .error(R.drawable.place_holder)

                .placeholder(circularProgressDrawable)
                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
    }
}