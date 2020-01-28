package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener

class SearchResultsAdapter: RecyclerView.Adapter<SearchViewHolder>, Filterable  {

    private var mContext: Context
    private var mListener: OnItemClickListener
    private var mProducts: ArrayList<Product>
    private var mFilteredProducts: ArrayList<Product>
    private var emptyList =  ArrayList<Product>()

    constructor(context: Context, products: ArrayList<Product>, listener: OnItemClickListener) : super() {
        this.mContext = context
        this.mProducts = products
        mFilteredProducts = emptyList
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.searched_items, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mFilteredProducts.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = mFilteredProducts[position]
        holder.name.text = product.name
        holder.bind(product, mListener)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val pattern = p0.toString()

                if(pattern.isEmpty()){
                    mFilteredProducts = emptyList
                }else{
                    for(product: Product in mProducts) {
                        if (product.name.toLowerCase().contains(pattern)) {
                            emptyList.add(product)
                        }
                    }
                    mFilteredProducts = emptyList
                }

                val results: FilterResults = FilterResults()
                results.values = mFilteredProducts
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }


}


