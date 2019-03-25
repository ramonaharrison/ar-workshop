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
        // TODO: Update when we actually have drawables for our objects.
        return when (this) {
            COFFEE -> R.drawable.ic_launcher_foreground
            PASTA -> R.drawable.ic_launcher_foreground
            PIZZA -> R.drawable.ic_launcher_foreground
            TIRAMISU -> R.drawable.ic_launcher_foreground
        }
    }
}