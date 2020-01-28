package com.ulan.app.munduz.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.searched_items.view.*

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name = itemView.findViewById<TextView>(R.id.searched_name)

    fun bind(product: Product?, onItemClickListener: OnItemClickListener){
        itemView.setOnClickListener{
            onItemClickListener.onItemClick(product)
        }
    }
}