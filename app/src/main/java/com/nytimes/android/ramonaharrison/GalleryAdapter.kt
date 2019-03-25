package com.nytimes.android.ramonaharrison

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter : RecyclerView.Adapter<GalleryViewHolder>() {

    private lateinit var selected: GalleryViewHolder
    private val items = ArModel.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, null, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val model = items[position]
        holder.bind(model)

        if (position == 0) {
            selected = holder
            selected.setSelected(true)
        }

        holder.itemView.setOnClickListener { updateSelected(holder) }
    }

    fun getSelected(): ArModel {
        return selected.model
    }

    private fun updateSelected(newSelection: GalleryViewHolder) {
        selected.setSelected(false)
        selected = newSelection
        selected.setSelected(true)
    }
}