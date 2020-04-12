package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.listeners.OnCategoryClickListener

class CatalogAdapter : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private var mContext: Context
    private var mListener: OnCategoryClickListener
    private lateinit  var catalog: MutableList<String>

    constructor(context: Context, listener: OnCategoryClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setCatalogs(catalog: MutableList<String>){
        this.catalog = catalog
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.catalog_item, parent, false)
        return CatalogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val category = catalog[position]
        holder.bind(category, mListener)
        holder.name.text = category
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.catalog_name)

        fun bind(category: String, listener: OnCategoryClickListener){
            itemView.setOnClickListener {
                listener.onCategoryClick(category)
            }
        }
    }
}