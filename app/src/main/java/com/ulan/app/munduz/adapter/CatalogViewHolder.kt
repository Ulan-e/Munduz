package com.ulan.app.munduz.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.listeners.OnCategoryClickListener

class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name = itemView.findViewById<TextView>(R.id.catalog_name)

    fun bind(category: String, listener: OnCategoryClickListener){
        itemView.setOnClickListener {
            listener.onCategoryClick(category)
        }
    }
}