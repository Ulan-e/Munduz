package com.ulan.app.munduz.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.repository.BaseRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener

class FavoriteProductAdapter: RecyclerView.Adapter<FavoriteProductViewHolder> {

    private var mContext: Context
    private var mListener: OnItemClickListener
    private lateinit var mProducts: ArrayList<Product>
    private lateinit var mRepository: BaseRepository

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(products: ArrayList<Product>){
        this.mProducts = products
    }

    fun setRepository(repository: BaseRepository){
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: FavoriteProductViewHolder, position: Int) {
        val product = mProducts[position]
        holder.bind(product, mListener)
        val rub = Html.fromHtml(" &#x20bd")
        Picasso.get()
            .load(product.picture.urlImage)
            .resize(250, 250)
            .centerCrop()
            .error(R.drawable.ic_error_image_black_24dp)
            .into(holder.image)
        holder.name.text = product.name
        holder.category.text = product.category
        holder.price.text = product.cost.toString() + " " + rub

        //Click Handle (Favorite)
        holder.favorite.setOnClickListener {
            if (mRepository.isExist(product.key)) {
                mProducts.removeAt(position)
                mRepository.remove(product.key)
                updateAfterRemoving(position)
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