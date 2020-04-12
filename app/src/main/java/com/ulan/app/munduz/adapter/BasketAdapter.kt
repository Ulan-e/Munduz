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
import com.ulan.app.munduz.listeners.OnChangeSumListener
import com.ulan.app.munduz.listeners.OnItemBasketClickListener
import java.util.regex.Matcher
import java.util.regex.Pattern


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
            .resize(250, 250)
            .centerCrop()
            .error(R.drawable.ic_error_image_black_24dp)
            .into(holder.image)
        holder.name.text = purchase.name
        holder.price.text = purchase.priceIncreased.toString() + " " + RUBLE
        holder.perPrice.text = purchase.perPriceIncreased

        var initialPrice = purchase.price
        var initialWeight = convertToInt(purchase.perPrice)

        holder.increase.setOnClickListener {
            purchase.priceIncreased += initialPrice
            var resWeight = convertToInt(purchase.perPriceIncreased) + initialWeight
            purchase.perPriceIncreased = resWeight.toString() + convertToString(purchase.perPrice)
            mRepository.update(purchase)
            mChangeListener.onSumChange()
            holder.price.text = purchase.priceIncreased.toString() + " " + RUBLE
            holder.perPrice.text = purchase.perPriceIncreased
        }

        holder.decrease.setOnClickListener {
            var priceNew = purchase.priceIncreased
            var weightNew = convertToInt(purchase.perPriceIncreased)
            if (priceNew != purchase.price) {
                priceNew -= initialPrice
                var resWeight = weightNew - initialWeight
                purchase.priceIncreased = priceNew
                purchase.perPriceIncreased =
                    resWeight.toString() + convertToString(purchase.perPrice)
                mRepository.update(purchase)
                mChangeListener.onSumChange()
                holder.price.text = purchase.priceIncreased.toString() + " " + RUBLE
                holder.perPrice.text = purchase.perPriceIncreased
            }
        }

        holder.remove.setOnClickListener {
            if (mRepository.isExist(purchase)) {
                mPurchases.removeAt(position)
                mRepository.remove(purchase)
                updateAfterRemoving(position)
                mChangeListener.onSumChange()
            }
        }
    }

    private fun updateAfterRemoving(position: Int) {
        notifyItemRemoved(position)
        notifyItemChanged(position)
        notifyItemRangeChanged(position, mPurchases.size)
        notifyDataSetChanged()
    }

    private fun convertToInt(weight: String): Int {
        var result = 0
        val p: Pattern = Pattern.compile("\\d+")
        val m: Matcher = p.matcher(weight)
        while (m.find()) {
            result = m.group().toInt()
        }
        return result
    }

    private fun convertToString(weight: String): String {
        return weight.replace(Regex("\\d+"), "")
    }

}