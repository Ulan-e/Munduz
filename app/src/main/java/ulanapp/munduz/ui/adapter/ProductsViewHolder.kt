package ulanapp.munduz.ui.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.interfaces.OnItemClickListener

class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image: ImageView = itemView.findViewById(R.id.product_image)
    val name: TextView = itemView.findViewById(R.id.product_name)
    val price: TextView = itemView.findViewById(R.id.product_price)
    val favorite: ImageView = itemView.findViewById(R.id.favorite)
    val basket: Button = itemView.findViewById(R.id.in_out_basket)

    fun bind(product: Product, onItemClickListener: OnItemClickListener) {
        itemView.setOnClickListener {
            onItemClickListener.onItemClick(product)
        }
    }
}