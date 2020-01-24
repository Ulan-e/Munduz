package com.ulan.app.munduz.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener
import kotlinx.android.synthetic.main.product_item.view.*

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.item_image)
    val name = itemView.findViewById<TextView>(R.id.item_name)
    val price = itemView.findViewById<TextView>(R.id.item_price)

    fun bind(product: Product, onItemClickListener: OnItemClickListener){
        itemView.setOnClickListener{
            onItemClickListener.onItemClick(product)
        }
    }

}