package com.nytimes.android.ramonaharrison

import android.net.Uri
import androidx.annotation.DrawableRes

enum class ArModel {
    COFFEE,
    PASTA,
    PIZZA,
    TIRAMISU;

    fun getUri(): Uri {
        return Uri.parse("${this.name.toLowerCase()}.sfb")
    }

    @DrawableRes
    fun getDrawable(): Int {
        return when (this) {
            COFFEE -> R.drawable.thumb_coffee
            PASTA -> R.drawable.thumb_pasta
            PIZZA -> R.drawable.thumb_pizza
            TIRAMISU -> R.drawable.thumb_tiramisu
        }
    }
}