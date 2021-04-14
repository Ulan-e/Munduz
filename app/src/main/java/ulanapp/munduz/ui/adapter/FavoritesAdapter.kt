package ulanapp.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.helpers.setSmallImage
import ulanapp.munduz.interfaces.OnItemClickListener

class FavoritesAdapter(
    private var context: Context,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<FavoritesViewHolder>() {

    private lateinit var products: MutableList<Product>
    private lateinit var repository: FavoritesRepository

    fun setProducts(products: MutableList<Product>) {
        this.products = products
    }

    fun setRepository(repository: FavoritesRepository) {
        this.repository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.favorite_product_item, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product, listener)

        setSmallImage(context, product.picture.urlImage, holder.image)
        holder.name.text = product.name
        holder.category.text = product.category
        holder.price.text = product.cost.toString() + " " + RUBLE

        holder.remove.setOnClickListener {
            removeProduct(product, position, holder)
        }
    }

    private fun removeProduct(product: Product, position: Int, holder: FavoritesViewHolder) {
        if (repository.isExist(product.id)) {
            repository.remove(product.id)
            products.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, products.size)
            notifyDataSetChanged()
        }
    }
}