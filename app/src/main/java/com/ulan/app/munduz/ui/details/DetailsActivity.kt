package com.ulan.app.munduz.ui.details

import android.content.Intent
import android.os.Bundle
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
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_OPEN_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_TURN_OFF_ADD_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.IN_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.OPEN_BASKET_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.TURN_OFF_ARG
import com.ulan.app.munduz.helpers.RUBLE
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.main.MainActivity
import kotlinx.android.synthetic.main.details_layout.*
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var mPresenter: DetailsPresenter

    private lateinit var mProduct: Product
    private var mMenu: Menu? = null
    private var mView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)

        mProduct = intent.getParcelableExtra<Product>(EXTRA_PRODUCT_ARG)
        val turn = intent.getStringExtra(EXTRA_TURN_OFF_ADD_BASKET)
        if(turn == TURN_OFF_ARG){
            add_to_basket.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
            add_to_basket.isEnabled = false
        }

        mPresenter.setToolbar()
        mPresenter.setProduct(mProduct)

        add_to_basket.setOnClickListener {
            mPresenter.addToBasketClicked()
        }
    }

    override fun closeDetails() {
        finish()
    }

    override fun goToBasket() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_OPEN_BASKET, OPEN_BASKET_ARG)
        startActivity(intent)
    }

    override fun showToolbar() {
        setSupportActionBar(product_toolbar)
        product_toolbar.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
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
        product_name.text = product.name
        product_desc.text = product.desc
        product_price.text = product.cost.toString() + " " + RUBLE
        product_priceFor.text = "Цена за " + product.priceFor
    }

    override fun changeAddToBasketText(text: String) {
        add_to_basket.text = text
        if(text == IN_BASKET){
            add_to_basket.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        }else{
            add_to_basket.setBackgroundColor(resources.getColor(R.color.purple))
        }
    }

    override fun addToBasket() {
        mPresenter.addToBasketClicked()
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

    override fun markAsFavorite() {
        mMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
    }

    override fun markAsNotFavorite() {
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
