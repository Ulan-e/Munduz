package com.ulan.app.munduz.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.roomdatabase.RoomRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnFavoriteItemClickListener
import com.ulan.app.munduz.listeners.OnItemClickListener

class FavoriteProductAdapter: RecyclerView.Adapter<FavoriteProductViewHolder> {

    private var mContext: Context
    private var mListener: OnFavoriteItemClickListener
    private lateinit var mProducts: MutableList<Product>
    private lateinit var mRepository: RoomRepository

    constructor(context: Context, listener: OnFavoriteItemClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(products: MutableList<Product>){
        this.mProducts = products
    }

    fun setRepository(repository: RoomRepository){
        this.mRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteProductViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view  = inflater.inflate(R.layout.favorite_product_item, parent, false)
        return FavoriteProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  mProducts.size
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        val product = mProducts.get(position)
        holder.bind(product, mListener)
        val rub = Html.fromHtml(" &#x20bd")
        Picasso.get()
            .load(product.picture.urlImage)
            .resize(250, 250)
            .centerCrop()
            .error(R.drawable.ic_error_image_black_24dp)
            .into(holder.image)
        holder.name.text = product.name
        holder.price.text = product.cost.toString() + " " + rub

        //Mark as favorite if product is in LikedDatabase
        if(mRepository.isKeyExists(product.id)){
            holder.favorite.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_favorite_black_24dp))
        }else{
            holder.favorite.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_favorite_border_24dp))
        }

        //Click Handle (Favorite)
        holder.favorite.setOnClickListener {
            if (mRepository.isKeyExists(product.id)) {
                mProducts.removeAt(position)
                mRepository.remove(product.id)
                holder.favorite.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_favorite_border_24dp))
                updateAfterRemoving(position)
            } else {
                mRepository.insert(product.id)
                holder.favorite.setImageDrawable(mContext.resources.getDrawable(R.drawable.ic_favorite_black_24dp))
            }
        }
    }

    private fun updateAfterRemoving(position: Int){
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, mProducts.size)
        notifyDataSetChanged()
    }
}