package com.ulan.app.munduz.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.room.entities.PurchaseEntity
import com.ulan.app.munduz.data.room.repository.BaseRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.listeners.OnChangeCountListener
import com.ulan.app.munduz.listeners.PurchaseClickListener
import java.util.regex.Matcher
import java.util.regex.Pattern


class BasketAdapter : RecyclerView.Adapter<BasketViewHolder> {

    private var mContext: Context
    private var mListener: PurchaseClickListener
    private lateinit var mChangeListener: OnChangeCountListener
    private lateinit var mPurchases : ArrayList<PurchaseEntity>
    private lateinit var mRepository: PurchasesRepository

    constructor(context: Context, listener: PurchaseClickListener) : super() {
        this.mContext = context
        this.mListener = listener
    }

    fun setProducts(purchases : ArrayList<PurchaseEntity>) {
        this.mPurchases = purchases
    }

    fun setListener(sumChangeListener: OnChangeCountListener) {
        this.mChangeListener = sumChangeListener
    }

    fun setRepository(repository: PurchasesRepository) {
        this.mRepository = repository
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.basket_product_item, parent, false)
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
        val rub = Html.fromHtml(" &#x20bd")
        Picasso.get()
            .load(purchase.picture!!.urlImage)
            .resize(250, 250)
            .centerCrop()
            .error(R.drawable.ic_error_image_black_24dp)
            .into(holder.image)
        holder.name.text = purchase.name
        holder.price.text = purchase.cost.toString() + " " + rub
        holder.priceFor.text = purchase.priceFor

        var price = purchase.cost
        var weight = convertToInt(purchase.priceFor)


        holder.plus.setOnClickListener {
            var priceNew = convertToInt(holder.price.text.toString())
            var weightNew = convertToInt(holder.priceFor.text.toString())
            var resPrice = price + priceNew
            var resWeight = weight + weightNew
            mChangeListener.incrementProduct(resPrice)
            mChangeListener.changeSumOfPurchases(resPrice)
            holder.price.text = resPrice.toString() + " " + rub
            holder.priceFor.text = resWeight.toString() + convertToString(purchase.priceFor)
        }

        holder.minus.setOnClickListener {
            var priceNew = convertToInt(holder.price.text.toString())
            var weightNew = convertToInt(holder.priceFor.text.toString())
            if (priceNew != purchase.cost) {
                var resPrice = priceNew - price
                var resWeight = weightNew - weight
                mChangeListener.decrementProduct(resPrice)
                holder.price.text = resPrice.toString() + " " + rub
                holder.priceFor.text = resWeight.toString() + convertToString(purchase.priceFor)
            }
        }

        holder.favorite.setOnClickListener {
            if (mRepository.isExist(purchase)) {
                mPurchases.removeAt(position)
                mRepository.remove(purchase)
                updateAfterRemoving(position)
                var priceNew = convertToInt(holder.price.text.toString())
                mChangeListener.decrementProduct(priceNew)
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