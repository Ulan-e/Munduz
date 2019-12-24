package com.ulan.app.munduz.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.core.persistence.PruneForest
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import kotlin.collections.MutableList as MutableList1

class SearchProductAdapter: RecyclerView.Adapter<SearchProductAdapter.ProductHolder>, Filterable {

    val context: Context
    val initialList: ArrayList<Product>
    val fullList: ArrayList<Product>

    constructor(context: Context, list: ArrayList<Product> ){
        this.context = context
        this.initialList = list
        fullList = ArrayList(initialList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return initialList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = initialList.get(position)
        holder.productName.text = product.name
    }

    class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productName = itemView.findViewById<TextView>(R.id.product_name)
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Product>()

                if(p0 == null || p0.length == 0){
                    filteredList.addAll(fullList)
                }else{
                    val pattern = p0.toString().toLowerCase().trim()

                    for(product: Product in fullList){
                        if(product.name.toLowerCase().contains(pattern)){
                            filteredList.add(product)
                        }
                    }
                }

                val results: FilterResults = FilterResults()
                results.values = filteredList

                return results

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                initialList.clear()
                initialList.addAll(p1?.values as List<Product>)
                notifyDataSetChanged()
            }

        }
    }
}


