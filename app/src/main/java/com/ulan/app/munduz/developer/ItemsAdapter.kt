package com.ulan.app.munduz.developer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R

class ItemsAdapter: RecyclerView.Adapter<ItemsAdapter.Holder> {

    private var proItems: MutableList<Product?> = mutableListOf()
    private var context: Context?
    private val onItemClickListener: OnItemClickListener?

    constructor(context: Context, items: MutableList<Product?>, listener: OnItemClickListener){
        this.context = context
        this.proItems = items
        this.onItemClickListener = listener
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : Holder {
        var inflater: LayoutInflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.items, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return proItems.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var product: Product? = proItems.get(position)
        holder.bind(product, onItemClickListener)
        holder.item_name.text = product!!.name
        holder.item_category.text = "Testing Mode"
        holder.item_cost.text = product.cost.toString()
    }



    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_name = itemView.findViewById<TextView>(R.id.item_name)
        val item_category = itemView.findViewById<TextView>(R.id.item_cost)
        val item_cost = itemView.findViewById<TextView>(R.id.item_cost)

        fun bind(product: Product?, onItemClickListener: OnItemClickListener?){
            itemView.setOnClickListener{
                onItemClickListener!!.onClickItem(product)
            }
        }
    }
}