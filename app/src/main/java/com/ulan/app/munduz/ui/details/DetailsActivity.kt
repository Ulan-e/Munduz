package com.ulan.app.munduz.ui.details

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.buy.BuyFragment
import com.ulan.app.munduz.ui.main.MainActivity
import kotlinx.android.synthetic.main.details_layout.*
import java.lang.Exception
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var mPresenter: DetailsPresenter

    private var mMenu: Menu? = null
    private lateinit var mProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)

        mProduct = intent.getParcelableExtra<Product>(PRODUCT_ARG)

        mPresenter.setToolbar()
        mPresenter.setProduct(mProduct)

        buy_product.setOnClickListener {
            mPresenter.buyButtonClicked()
        }

    }

    override fun closeDetails() {
        finish()
    }

    override fun showToolbar() {
        setSupportActionBar(product_toolbar)
        product_toolbar.title = mProduct.name
        image_progress_bar.visibility = View.VISIBLE
        Picasso.get()
            .load(mProduct.picture.urlImage)
            .fit()
            .into(details_image, object : Callback {
                override fun onSuccess() {
                    image_progress_bar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    Log.e(Constants.TAG, "Error loading image")
                }

            })
        product_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        product_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun showEmptyData() {
        empty_product.visibility = View.VISIBLE
    }

    override fun showProduct(product: Product) {
        val rub = Html.fromHtml(" &#x20bd")
        product_name.text = product.name
        product_desc.text = product.desc
        product_price.text = product.cost.toString() + " " + rub
        product_priceFor.text = product.priceFor
    }

    override fun showOrderProduct() {
        val buyFragment = BuyFragment.newInstance(mProduct)
        buyFragment.show(supportFragmentManager, "buy_dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.mMenu = menu
        mPresenter.isFavoriteProduct()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.favorite -> {
                mPresenter.favoriteClicked()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun markAsLiked() {
        mMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
    }

    override fun markAsNotLiked() {
        mMenu?.getItem(0)?.icon =
            ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_24dp)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
