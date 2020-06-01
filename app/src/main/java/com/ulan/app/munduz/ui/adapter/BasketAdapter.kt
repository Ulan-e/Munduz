package com.ulan.app.munduz.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.helpers.convertIntToString
import com.ulan.app.munduz.helpers.convertStringToInt
import com.ulan.app.munduz.interfaces.OnChangeSumListener
import com.ulan.app.munduz.interfaces.OnItemBasketClickListener


class BasketAdapter : RecyclerView.Adapter<BasketViewHolder> {

    private var context: Context
    private var itemClickListener: OnItemBasketClickListener

    private lateinit var sumChangeListener: OnChangeSumListener
    private lateinit var purchases: MutableList<PurchaseEntity>
    private lateinit var purchasesRepository: PurchasesRepository

    constructor(context: Context, listener: OnItemBasketClickListener) : super() {
        this.context = context
        this.itemClickListener = listener
    }

    fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.purchases = purchases
    }

    fun setListener(listener: OnChangeSumListener) {
        this.sumChangeListener = listener
    }

    fun setRepository(repository: PurchasesRepository) {
        this.purchasesRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.basket_purchase_item, parent, false)
        return BasketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return purchases.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val purchase = purchases[position]
        holder.bind(purchase, itemClickListener)
        Picasso.get()
            .load(purchase.picture.urlImage)
            .error(R.drawable.ic_error_image_black_24dp)
            .fit()
            .into(holder.image)
        holder.name.text = purchase.name
        holder.price.text = purchase.priceInc.toString() + RUBLE
        holder.perPrice.text = purchase.perPriceInc

        val initialPrice = purchase.price
        val initialPerPrice = convertStringToInt(purchase.perPrice)
        var changedPerPrice = convertStringToInt(purchase.perPriceInc)
        var changedPrice = purchase.priceInc
        val perCount = convertIntToString(purchase.perPrice)

        holder.increase.setOnClickListener {
            changedPrice += initialPrice
            changedPerPrice += initialPerPrice
            purchase.priceInc = changedPrice
            purchase.perPriceInc = changedPerPrice.toString() + perCount
            updateValues(holder, purchase)
        }

        holder.decrease.setOnClickListener {
            if (changedPrice != purchase.price) {
                changedPrice -= initialPrice
                changedPerPrice -= initialPerPrice
                purchase.priceInc = changedPrice
                purchase.perPriceInc = changedPerPrice.toString() + perCount
                updateValues(holder, purchase)
            }
        }

        holder.remove.setOnClickListener {
            if (purchasesRepository.isExist(purchase.id)) {
                purchases.removeAt(position)
                purchasesRepository.remove(purchase.id)
                updateAfterRemoving(position)
            }
        }
    }

    private fun updateValues(holder: BasketViewHolder, purchase: PurchaseEntity) {
        purchasesRepository.update(purchase)
        sumChangeListener.onSumChanged()
        holder.price.text = purchase.priceInc.toString() + RUBLE
        holder.perPrice.text = purchase.perPriceInc
    }

    private fun updateAfterRemoving(position: Int) {
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, purchases.size)
        notifyDataSetChanged()
        sumChangeListener.onSumChanged()
    }

}