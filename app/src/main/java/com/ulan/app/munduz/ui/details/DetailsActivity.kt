package com.ulan.app.munduz.ui.details

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.roomdatabase.LikedDatabase
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.helpers.showNoProduct
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.buy.BuyFragment
import kotlinx.android.synthetic.main.details_layout.*

class DetailsActivity : BaseActivity(), DetailsView {

    private lateinit var mProduct: Product
    private lateinit var mPresenter: DetailsPresenter
    private lateinit var mDatabase: LikedDatabase
    private var mMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)

        mDatabase = LikedDatabase.getDatabase(this)!!
        mProduct = intent.getParcelableExtra<Product>(PRODUCT_ARG)
        mPresenter = DetailsPresenterImpl(this, mDatabase)
        mPresenter.setToolbar()
        mPresenter.setProduct(mProduct)


        buy_product.setOnClickListener {
            mPresenter.buyButtonClicked()
        }

    }

    override fun initToolbar(title: String) {
        setSupportActionBar(product_toolbar)
        Picasso.get().load(mProduct.picture.urlImage).into(details_image)
        product_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        product_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun closeDetails() {
        finish()
    }

    override fun showProduct(product: Product) {
        product_name.text = product.name
        product_desc.text = product.desc
        product_price.text = product.cost.toString()
        product_amount.text = product.amount.toString()
    }

    override fun showNoProduct(text: String) {
        showNoProduct(this)
    }

    override fun showOrderProduct() {
        val buyFragment = BuyFragment.newInstance(mProduct)
        buyFragment.show(supportFragmentManager, "buy_dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.mMenu = menu
        isFavoriteProduct()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.favorite -> {
                mPresenter.favoriteButtonClicked()
                disableFavoriteButton()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun disableFavoriteButton() {
        mMenu?.getItem(0)?.setIcon(
            ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        )
        mMenu?.getItem(0)?.isEnabled = false
        mMenu?.getItem(0)?.isCheckable = false
        mMenu?.getItem(0)?.isChecked = false
    }

    private fun isFavoriteProduct() {
        val key = mProduct.id
        val table = mDatabase.productsDao().fetchAllKeys()
        for (item in table) {
            if (key == item.key) {
                disableFavoriteButton()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
