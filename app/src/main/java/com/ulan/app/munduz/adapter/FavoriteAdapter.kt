package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.listeners.OnItemClickListener

class FavoriteAdapter: RecyclerView.Adapter<FavoriteViewHolder> {

    private var mContext: Context
    private var mListener: OnItemClickListener
    private lateinit var mProducts: MutableList<Product>
    private lateinit var mRepository: FavoritesRepository

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(products: MutableList<Product>){
        this.mProducts = products
    }

    fun setRepository(repository: FavoritesRepository){
        this.mRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view  = inflater.inflate(R.layout.favorite_product_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  mProducts.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val product = mProducts.get(position)
        holder.bind(product, mListener)
        Picasso.get()
            .load(product.picture.urlImage)
            .resize(250, 250)
            .centerCrop()
            .error(R.drawable.ic_error_image_black_24dp)
            .into(holder.image)
        holder.name.text = product.name
        holder.category.text = product.category
        holder.price.text = product.cost.toString() + " " + RUBLE

        //Remove after click 'favorite'
        holder.remove.setOnClickListener {
            if (mRepository.isExist(product.id)) {
                mProducts.removeAt(position)
                mRepository.remove(product.id)
                updateAfterItemRemoved(position)
            }
        }
    }

    private fun updateAfterItemRemoved(position: Int){
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, mProducts.size)
        notifyDataSetChanged()
    }
}