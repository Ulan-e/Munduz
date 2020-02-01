package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.listeners.OnCategoryClickListener

class CatalogAdapter : RecyclerView.Adapter<CatalogViewHolder> {

    private var context: Context
    private var listener: OnCategoryClickListener

    private lateinit  var catalog: MutableList<String>

    constructor(context: Context, listener: OnCategoryClickListener) : super() {
        this.context = context
        this.listener = listener
    }

    fun setCatalogs(catalog: MutableList<String>){
        this.catalog = catalog
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.catalog_item, parent, false)
        return CatalogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return catalog.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val category = catalog[position]
        holder.bind(category, listener)
        holder.name.text = category
    }
}