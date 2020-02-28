package com.ulan.app.munduz.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnFavoriteItemClickListener

class FavoriteProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.item_image)
    val name = itemView.findViewById<TextView>(R.id.item_name)
    val price = itemView.findViewById<TextView>(R.id.item_price)
    val buyButton = itemView.findViewById<TextView>(R.id.buy_button)
    val favorite = itemView.findViewById<ImageView>(R.id.item_favorite)

    fun bind(product: Product, onFavoriteItemClickListener: OnFavoriteItemClickListener){
        itemView.setOnClickListener{
            onFavoriteItemClickListener.onItemClick(product)
        }

        buyButton.setOnClickListener{
            onFavoriteItemClickListener.onBuyClick(product)
        }
    }

}