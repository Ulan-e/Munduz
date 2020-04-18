package com.ulan.app.munduz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.helpers.convertStringToInt
import com.ulan.app.munduz.helpers.convertIntToString
import com.ulan.app.munduz.listeners.OnChangeSumListener
import com.ulan.app.munduz.listeners.OnItemBasketClickListener


class BasketAdapter : RecyclerView.Adapter<BasketViewHolder> {

    private var mContext: Context
    private var mListener: OnItemBasketClickListener
    private lateinit var mChangeListener: OnChangeSumListener
    private lateinit var mPurchases: MutableList<PurchaseEntity>
    private lateinit var mRepository: PurchasesRepository

    constructor(context: Context, listener: OnItemBasketClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(purchases: MutableList<PurchaseEntity>) {
        this.mPurchases = purchases
    }

    fun setListener(listener: OnChangeSumListener) {
        this.mChangeListener = listener
    }

    fun setRepository(repository: PurchasesRepository) {
        this.mRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.basket_purchase_item, parent, false)
        return BasketViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPurchases.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val purchase = mPurchases[position]
        holder.bind(purchase, mListener)
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
            if (mRepository.isExist(purchase.id)) {
                mPurchases.removeAt(position)
                mRepository.remove(purchase.id)
                updateAfterRemoving(position)
            }
        }
    }

    private fun updateValues(holder: BasketViewHolder, purchase: PurchaseEntity) {
        mRepository.update(purchase)
        mChangeListener.onSumChanged()
        holder.price.text = purchase.priceInc.toString() + RUBLE
        holder.perPrice.text = purchase.perPriceInc
    }

    private fun updateAfterRemoving(position: Int) {
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, mPurchases.size)
        notifyDataSetChanged()
        mChangeListener.onSumChanged()
    }

}