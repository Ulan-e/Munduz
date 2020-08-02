package ulanapp.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.interfaces.OnItemClickListener

class SearchAdapter(
    private var context: Context,
    listener: OnItemClickListener) :
        RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), Filterable {

    private var itemClickListener: OnItemClickListener = listener

    private var emptyProducts = mutableListOf<Product>()
    private lateinit var initialProducts: MutableList<Product>
    private lateinit var filterProducts: MutableList<Product>

    fun setProducts(products: MutableList<Product>) {
        this.initialProducts = products
        filterProducts = emptyProducts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.searched_items, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterProducts.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = filterProducts[position]
        holder.name.text = product.name
        holder.bind(product, itemClickListener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val pattern = p0.toString()

                if (pattern.isEmpty()) {
                    filterProducts = emptyProducts
                } else {
                    val innerList = ArrayList<Product>()
                    for (product: Product in initialProducts) {
                        if (product.name.toLowerCase().contains(pattern) || product.desc.toLowerCase().contains(pattern)) {
                            innerList.add(product)
                        }
                    }
                    filterProducts = innerList
                }

                val results: FilterResults = FilterResults()
                results.values = filterProducts
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.searched_name)

        fun bind(product: Product?, onItemClickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                if (product != null) {
                    onItemClickListener.onItemClick(product)
                }
            }
        }
    }

}


