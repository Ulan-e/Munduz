package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.listeners.OnCategoryClickListener
import com.ulan.app.munduz.listeners.OnItemClickListener

class CatalogAdapter : RecyclerView.Adapter<CatalogViewHolder> {

    private var context: Context
    private var catalogs: MutableList<String>
    private var listener: OnCategoryClickListener

    constructor(context: Context, catalogs: MutableList<String>, listener: OnCategoryClickListener) : super() {
        this.catalogs = catalogs
        this.context = context
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.catalog_item, parent, false)
        return CatalogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return catalogs.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val category = catalogs[position]
        holder.bind(category, listener)
        holder.name.text = category
    }
}