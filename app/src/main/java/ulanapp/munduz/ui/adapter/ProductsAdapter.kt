package ulanapp.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Product
import ulanapp.munduz.data.room.repository.FavoritesRepository
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.helpers.Constants.Companion.ALREADY_IN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.helpers.setSmallImage
import ulanapp.munduz.interfaces.OnItemClickListener

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder> {

    private var context: Context
    private var listener: OnItemClickListener
    private lateinit var products: MutableList<Product>
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var purchasesRepository: PurchasesRepository

    constructor(context: Context, listener: OnItemClickListener) : super() {
        this.context = context
        this.listener = listener
    }

    fun setProducts(products: MutableList<Product>) {
        this.products = products
    }

    fun setRepositories(favorites: FavoritesRepository, purchases: PurchasesRepository) {
        this.favoritesRepository = favorites
        this.purchasesRepository = purchases
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.product_item, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product, listener)

        setSmallImage(product.picture.urlImage, holder.image)
        holder.name.text = product.name
        holder.price.text = product.cost.toString() + RUBLE

        if (favoritesRepository.isExist(product.id)) {
            setAsFavorite(holder)
        } else {
            setAsNotFavorite(holder)
        }

        if (purchasesRepository.isExist(product.id)) {
            setToBasket(holder)
        } else {
            setNotInBasket(holder)
        }

        holder.favorite.setOnClickListener {
            if (favoritesRepository.isExist(product.id)) {
                favoritesRepository.remove(product.id)
                setAsNotFavorite(holder)
            } else {
                favoritesRepository.insert(product.id)
                setAsFavorite(holder)
            }
        }

        holder.basket.setOnClickListener {
            if (purchasesRepository.isExist(product.id)) {
                purchasesRepository.remove(product.id)
                setNotInBasket(holder)
            } else {
                purchasesRepository.insert(product)
                setToBasket(holder)
                Toast.makeText(context, "Добавлен в Корзину", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setNotInBasket(holder: ProductsViewHolder) {
        holder.basket.apply {
            setBackgroundColor(context.resources.getColor(R.color.colorPrimary))
            setTextColor(context.resources.getColor(R.color.white))
            text = NOT_IN_BASKET
        }
    }

    private fun setToBasket(holder: ProductsViewHolder) {
        holder.basket.apply {
            setBackgroundColor(context.resources.getColor(R.color.white))
            setTextColor(context.resources.getColor(R.color.colorPrimary))
            text = ALREADY_IN_BASKET
        }
    }

    private fun setAsFavorite(viewHolder: ProductsViewHolder) {
        viewHolder.favorite.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_black_24dp
            )
        )
    }

    private fun setAsNotFavorite(viewHolder: ProductsViewHolder) {
        viewHolder.favorite.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_border_24dp
            )
        )
    }

}