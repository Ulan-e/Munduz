package ulanapp.munduz.ui.activities.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ulanapp.munduz.data.models.Product
import kotlinx.android.synthetic.main.details_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.helpers.Constants
import ulanapp.munduz.helpers.Constants.Companion.BASKET_TURN_OFF
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_OPEN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_TURN_OFF_ADD_BASKET
import ulanapp.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.OPEN_BASKET_ARG
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.ui.activities.main.MainActivity
import ulanapp.munduz.ui.base.BaseActivity
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenterImpl

    private lateinit var product: Product
    private lateinit var basketSwitcher: String

    private var menu: Menu? = null
    private var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)

        presenter.bindView(this)

        product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT_ARG)
        basketSwitcher = intent.getStringExtra(EXTRA_TURN_OFF_ADD_BASKET)

        disableBasketButton(basketSwitcher)

        presenter.apply{
            setToolbar()
            setProduct(product)
            isInBasket()
        }


        add_to_basket.setOnClickListener {
            presenter.addToBasketClicked()
        }
    }

    private fun disableBasketButton(basketSwitcher: String?) {
        if (basketSwitcher != null) {
            if (basketSwitcher == BASKET_TURN_OFF) {
                add_to_basket.apply{
                    setBackgroundColor(resources.getColor(R.color.white))
                    setTextColor(resources.getColor(R.color.colorPrimary))
                    text = Constants.ALREADY_IN_BASKET
                    isClickable = false
                    isEnabled = false
                }
            }
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
        toolbarSettings()
        toolbarImage()
    }

    private fun toolbarSettings() {
        setSupportActionBar(product_toolbar)
        product_toolbar.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        product_toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        product_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun toolbarImage() {
        image_progress_bar.visibility = View.VISIBLE
        Picasso.get()
            .load(product.picture.urlImage)
            .fit()
            .into(details_image, object : Callback {
                override fun onSuccess() {
                    image_progress_bar.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    details_image.setImageResource(R.drawable.ic_error_image_black_24dp)
                    Log.e(Constants.TAG, "Error loading image")
                }

            })
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

    override fun changeBasketText(title: String) {
        if (title == NOT_IN_BASKET) {
            add_to_basket.apply {
                setBackgroundColor(resources.getColor(R.color.colorPrimary))
                setTextColor(resources.getColor(R.color.white))
                text = Constants.NOT_IN_BASKET
            }

        } else {
            add_to_basket.apply {
                setBackgroundColor(resources.getColor(R.color.white))
                setTextColor(resources.getColor(R.color.colorPrimary))
                text = Constants.ALREADY_IN_BASKET
            }
        }
    }

    override fun addToBasket() {
        presenter.addToBasketClicked()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        this.menu = menu
        presenter.isFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.favorite -> {
                presenter.favoriteClicked()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun markAsFavorite() {
        menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
    }

    override fun markAsNotFavorite() {
        menu?.getItem(0)?.icon =
            ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_24dp)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }
}
