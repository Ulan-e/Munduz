package ulanapp.munduz.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.interfaces.OnItemClickListener

class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image: ImageView = itemView.findViewById(R.id.favorite_image)
    val name: TextView = itemView.findViewById(R.id.favorite_name)
    val price: TextView = itemView.findViewById(R.id.favorite_price)
    val category: TextView = itemView.findViewById(R.id.favorite_category)
    val remove: ImageView = itemView.findViewById(R.id.remove_purchase)

    fun bind(product: Product, onClickListener: OnItemClickListener) {
        itemView.setOnClickListener {
            onClickListener.onItemClick(product)
        }
    }
}