package com.ulan.app.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.interfaces.OnItemClickListener

class FavoritesAdapter : RecyclerView.Adapter<FavoritesViewHolder> {

    private var context: Context
    private var listener: OnItemClickListener
    private lateinit var products: MutableList<Product>
    private lateinit var repository: FavoritesRepository

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.context = context
        this.listener = listener
    }

    fun setProducts(products: MutableList<Product>) {
        this.products = products
    }

    fun setRepository(repository: FavoritesRepository) {
        this.repository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.favorite_product_item, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val product = products.get(position)
        holder.bind(product, listener)

        Picasso.get()
            .load(product.picture.urlImage)
            .error(R.drawable.ic_error_image_black_24dp)
            .fit()
            .into(holder.image)

        holder.name.text = product.name
        holder.category.text = product.category
        holder.price.text = product.cost.toString() + " " + RUBLE

        holder.remove.setOnClickListener {
            if (repository.isExist(product.id)) {
                products.removeAt(position)
                repository.remove(product.id)
                updateAfterItemRemoved(position)
            }
        }
    }

    private fun updateAfterItemRemoved(position: Int) {
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, products.size)
        notifyDataSetChanged()
    }

}