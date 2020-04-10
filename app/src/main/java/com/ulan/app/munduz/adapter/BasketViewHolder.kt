package com.ulan.app.munduz.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.listeners.PurchaseClickListener

class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image = itemView.findViewById<ImageView>(R.id.item_image)
    val name = itemView.findViewById<TextView>(R.id.item_name)
    val price = itemView.findViewById<TextView>(R.id.item_price)
    val priceFor = itemView.findViewById<TextView>(R.id.item_priceFor)
    val favorite = itemView.findViewById<ImageView>(R.id.item_favorite)

    val plus = itemView.findViewById<Button>(R.id.plus)
    val minus = itemView.findViewById<Button>(R.id.minus)

    fun bind(purchase: PurchaseEntity, purchaseClickListener: PurchaseClickListener) {
        itemView.setOnClickListener {
            purchaseClickListener.onItemClick(purchase)
        }
    }
}