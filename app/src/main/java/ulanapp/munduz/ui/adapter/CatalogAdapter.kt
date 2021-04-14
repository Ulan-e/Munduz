package ulanapp.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.interfaces.OnCategoryClickListener

class CatalogAdapter(
    private var context: Context,
    listener: OnCategoryClickListener) :
        RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private var categoryClickListener: OnCategoryClickListener = listener
    private lateinit var catalog: MutableList<String>
    private lateinit var images: IntArray

    fun setCatalogs(catalog: MutableList<String>) {
        this.catalog = catalog
    }

    fun setImages(images: IntArray) {
        this.images = images
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
        val image = images[position]
        holder.bind(category, this.categoryClickListener)
        holder.name.text = category
        holder.image.setImageResource(image)
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.catalog_name)
        val image: ImageView = itemView.findViewById(R.id.catalog_image)

        fun bind(category: String, listener: OnCategoryClickListener) {
            itemView.setOnClickListener {
                listener.onCategoryClick(category)
            }
        }
    }
}