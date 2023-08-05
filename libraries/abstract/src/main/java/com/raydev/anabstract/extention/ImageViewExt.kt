package com.raydev.anabstract.extention

import android.widget.ImageView
import androidx.core.content.ContextCompat


fun ImageView.setImageWithColor(picture: Int, color: Int) {
    this.setImageResource(picture)
    this.setColorFilter(ContextCompat.getColor(context, color))
}

//fun ImageView.load(url: String?) {
//    Glide.with(this).load(url).placeholder(R.mipmap.img_placeholder).error(R.mipmap.img_placeholder).into(this)
//}