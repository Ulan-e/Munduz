package com.ulan.app.munduz.ui.details

import android.os.Bundle
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.helpers.showNoProduct
import com.ulan.app.munduz.ui.base.BaseActivity
import kotlinx.android.synthetic.main.details_layout.*

class DetailsActivity : BaseActivity(), DetailsView{

    private lateinit var mProduct: Product
    private lateinit var mPresenter: DetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        mProduct = intent.getParcelableExtra<Product>(PRODUCT_ARG)

        mPresenter = DetailsPresenterImpl(this)
        mPresenter.setToolbar()

        if(mProduct != null) {
            mPresenter.setProduct(mProduct)
        }

        buy_product.setOnClickListener{
            mPresenter.buyButtonClicked()
        }

    }

    override fun initToolbar(title: String) {
        setSupportActionBar(product_toolbar)
        details_toolbar_text.text = mProduct.category
        product_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        product_toolbar.setNavigationOnClickListener{
            finish()
        }
    }

    override fun closeDetails() {
        finish()
    }

    override fun showProduct(product: Product) {
        Picasso.get().load(product.picture.urlImage).into(product_image)
        product_name.text = product.name
        product_desc.text = product.desc
        product_price.text = product.cost.toString()
        product_amount.text = product.amount.toString()
    }

    override fun showNoProduct(text: String) {
        showNoProduct(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}
