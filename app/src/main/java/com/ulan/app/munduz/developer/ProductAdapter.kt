package com.ulan.app.munduz.developer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.convertLongToTime

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.Holder> {

    private var products: ArrayList<Product?>
    private var context: Context?
    private val onItemClickListener: OnItemClickListener?

    constructor(context: Context, items: ArrayList<Product?>, listener: OnItemClickListener){
        this.context = context
        this.products = items
        this.onItemClickListener = listener
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : Holder {
        var inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.items, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var product: Product? = products.get(position)
        holder.bind(product, onItemClickListener)
        holder.item_name.text = product!!.name
        holder.item_category.text = product!!.category
        holder.item_cost.text = product.date.convertLongToTime(product!!.date)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_name = itemView.findViewById<TextView>(R.id.item_name)
        val item_category = itemView.findViewById<TextView>(R.id.item_category)
        val item_cost = itemView.findViewById<TextView>(R.id.item_cost)

        fun bind(product: Product?, onItemClickListener: OnItemClickListener?){
            itemView.setOnClickListener{
                onItemClickListener!!.onClickItem(product)
            }
        }
    }
}