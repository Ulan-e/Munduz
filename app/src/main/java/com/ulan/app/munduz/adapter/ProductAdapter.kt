package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>{

    private var context: Context
    private var listener: OnItemClickListener
    private lateinit var products:  MutableList<Product>

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.context = context
        this.listener = listener
    }

    fun setProducts(products: MutableList<Product>){
        this.products = products
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view  = inflater.inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products.get(position)
        holder.bind(product, listener)
        Picasso.get()
            .load(product.picture.urlImage)
            .into(holder.image)
        holder.name.text = product.name
        holder.price.text = product.cost.toString()
    }
}