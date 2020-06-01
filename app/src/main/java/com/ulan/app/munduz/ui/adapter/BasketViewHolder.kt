package com.ulan.app.munduz.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.interfaces.OnItemBasketClickListener

class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image: ImageView = itemView.findViewById(R.id.purchase_image)
    val name: TextView = itemView.findViewById(R.id.purchase_name)
    val price: TextView = itemView.findViewById(R.id.purchase_price)
    val perPrice: TextView = itemView.findViewById(R.id.purchase_per_price)
    val remove: ImageView = itemView.findViewById(R.id.remove_purchase)

    val increase: ImageView = itemView.findViewById(R.id.increment)
    val decrease: ImageView = itemView.findViewById(R.id.decrement)

    fun bind(purchase: PurchaseEntity, clickListener: OnItemBasketClickListener) {
        itemView.setOnClickListener {
            clickListener.onItemClick(purchase)
        }
    }
}