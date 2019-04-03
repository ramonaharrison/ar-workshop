package com.nytimes.android.ramonaharrison

import android.content.res.Resources
import android.graphics.Rect
import android.graphics.drawable.InsetDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView: ImageView by lazy {
        itemView.findViewById<ImageView>(R.id.item_gallery_image)
    }

    lateinit var model: ArModel
        private set

    fun bind(model: ArModel) {
        this.model = model
        Picasso.get().load(model.getDrawable()).resize(dpToPixels(100f), dpToPixels(100f)).into(imageView)
        imageView.contentDescription = model.name.toLowerCase()
    }

    fun setSelected(selected: Boolean) {
        if (selected) {
            addOverlay(dpToPixels(20f), dpToPixels(4f))
        } else {
            removeOverlay()
        }
    }

    private fun addOverlay(sizePx: Int, insetPx: Int) {
        imageView.doOnPreDraw {
            val resId = R.drawable.ic_check
            val overlayThumb = ResourcesCompat.getDrawable(imageView.resources, resId, null)!!
            overlayThumb.bounds = Rect(0, 0, sizePx, sizePx)
            val insetTop = imageView.height - sizePx - insetPx

            val insetDrawable = InsetDrawable(overlayThumb, insetPx, insetTop, 0, 0)
            insetDrawable.bounds = Rect(0, 0, insetPx + sizePx, insetTop + sizePx)

            imageView.overlay.add(insetDrawable)
        }
    }

    private fun removeOverlay() {
        imageView.overlay.clear()
    }

    private fun dpToPixels(dp: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return Math.ceil((dp * scale).toDouble()).toInt()
    }

}