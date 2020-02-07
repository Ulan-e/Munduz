package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener

class SearchResultsAdapter: RecyclerView.Adapter<SearchViewHolder>, Filterable  {

    private var mContext: Context
    private var mListener: OnItemClickListener
    private var emptyList =  ArrayList<Product>()

    private lateinit var mProducts: ArrayList<Product>
    private lateinit var mFilteredProducts: ArrayList<Product>

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(products : ArrayList<Product>){
        this.mProducts = products
        mFilteredProducts = emptyList
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
                    var innerList =  ArrayList<Product>()
                    for(product: Product in mProducts) {
                        if (product.name.toLowerCase().contains(pattern)) {
                            innerList.add(product)
                        }
                    }
                    mFilteredProducts = innerList
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


