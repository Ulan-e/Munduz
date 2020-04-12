package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.listeners.OnItemClickListener

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder> {

    private var mContext: Context
    private var mListener: OnItemClickListener
    private lateinit var mProducts: MutableList<Product>
    private lateinit var mRepository: FavoritesRepository

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(products: MutableList<Product>) {
        this.mProducts = products
    }

    fun setRepository(repository: FavoritesRepository) {
        this.mRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.product_item, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mProducts.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = mProducts.get(position)
        holder.bind(product, mListener)
        Picasso.get()
            .load(product.picture.urlImage)
            .fit()
            .into(holder.image)
        holder.name.text = product.name
        holder.price.text = product.cost.toString() + RUBLE

        //Mark as favorite if product is in Database
        if (mRepository.isExist(product.id)) {
            setAsFavorite(holder)
        } else {
            setAsNotFavorite(holder)
        }

        //Click Handle (Favorite)
        holder.addFavorite.setOnClickListener {
            if (mRepository.isExist(product.id)) {
                mRepository.remove(product.id)
                setAsNotFavorite(holder)
            } else {
                mRepository.insert(product.id)
                setAsFavorite(holder)
            }
        }
    }

    private fun setAsFavorite(viewHolder: ProductsViewHolder){
        viewHolder.addFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp))
    }

    private fun setAsNotFavorite(viewHolder: ProductsViewHolder){
        viewHolder.addFavorite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_border_24dp))
    }

}