package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.listeners.OnItemClickListener

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder> {

    private var mContext: Context
    private var mListener: OnItemClickListener
    private lateinit var mProducts: MutableList<Product>
    private lateinit var mFavoritesRepo: FavoritesRepository
    private lateinit var mPurchasesRepo: PurchasesRepository

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(products: MutableList<Product>) {
        this.mProducts = products
    }

    fun setRepositories(favorites: FavoritesRepository, purchases: PurchasesRepository) {
        this.mFavoritesRepo = favorites
        this.mPurchasesRepo = purchases
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
        if (mFavoritesRepo.isExist(product.id)) {
            setAsFavorite(holder)
        } else {
            setAsNotFavorite(holder)
        }

        if (mPurchasesRepo.isExist(product.id)) {
            setInAlreadyBasket(holder)
        } else {
            setNotInBasket(holder)
        }

        //Click Handle (Favorite)
        holder.favorite.setOnClickListener {
            if (mFavoritesRepo.isExist(product.id)) {
                mFavoritesRepo.remove(product.id)
                setAsNotFavorite(holder)
            } else {
                mFavoritesRepo.insert(product.id)
                setAsFavorite(holder)
            }
        }

        holder.in_out_basket.setOnClickListener {
            if (mPurchasesRepo.isExist(product.id)) {
                mPurchasesRepo.remove(product.id)
                setNotInBasket(holder)
            } else {
                mPurchasesRepo.insert(product)
                setInAlreadyBasket(holder)
            }
        }
    }

    private fun setNotInBasket(holder: ProductsViewHolder) {
        holder.in_out_basket.setBackgroundColor(mContext.resources.getColor(R.color.colorPrimary))
        holder.in_out_basket.setTextColor(mContext.resources.getColor(R.color.white))
        holder.in_out_basket.text = Constants.NOT_IN_BASKET
    }

    private fun setInAlreadyBasket(holder: ProductsViewHolder) {
        holder.in_out_basket.setBackgroundColor(mContext.resources.getColor(R.color.white))
        holder.in_out_basket.setTextColor(mContext.resources.getColor(R.color.colorPrimary))
        holder.in_out_basket.text = Constants.ALREADY_IN_BASKET
    }

    private fun setAsFavorite(viewHolder: ProductsViewHolder) {
        viewHolder.favorite.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_favorite_black_24dp
            )
        )
    }

    private fun setAsNotFavorite(viewHolder: ProductsViewHolder) {
        viewHolder.favorite.setImageDrawable(
            ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_favorite_border_24dp
            )
        )
    }

}