package ulanapp.munduz.ui.activities.details

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ulanapp.munduz.data.models.Product
import kotlinx.android.synthetic.main.details_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.repository.FirebaseRepository
import ulanapp.munduz.helpers.Constants
import ulanapp.munduz.helpers.Constants.Companion.BASKET_TURN_OFF
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_OPEN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_TURN_OFF_ADD_BASKET
import ulanapp.munduz.helpers.Constants.Companion.NOT_IN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.OPEN_BASKET_ARG
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.interfaces.ProductCallback
import ulanapp.munduz.ui.activities.main.MainActivity
import ulanapp.munduz.ui.adapter.DetailsImageAdapter
import ulanapp.munduz.ui.base.BaseActivity
import javax.inject.Inject

class DetailsActivity : BaseActivity(), DetailsView {

    @Inject
    lateinit var presenter: DetailsPresenterImpl

    @Inject
    lateinit var firebaseRepository: FirebaseRepository

    private var product: Product? = null
    private var basketSwitcher: String? = null

    private var detailsImageAdapter: DetailsImageAdapter? = null

    private var menu: Menu? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_layout)
        checkInternetConnection()

        presenter.bindView(this)

        // получаем аргументы
        product = intent.getParcelableExtra<Product>(EXTRA_PRODUCT_ARG)
        basketSwitcher = intent.getStringExtra(EXTRA_TURN_OFF_ADD_BASKET)

        // отключаем кнопку добавление в корзину
        disableBasketButton(basketSwitcher)

        presenter.apply {
            setToolbar()
            setProduct(product!!)
            isInBasket()
        }

        // клик на добавление в корзину
        add_to_basket.setOnClickListener {
            presenter.addToBasketClicked()
        }
    }

    override fun goToBasket() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(EXTRA_OPEN_BASKET, OPEN_BASKET_ARG)
        startActivity(intent)
    }

    override fun showToolbar() {
        toolbarSettings()
        headerImage()
    }

    override fun showEmptyData() {
        empty_product.visibility = View.VISIBLE
    }

    override fun showProduct(product: Product) {
        checkProductVisibility(product)
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
                iconTint = ContextCompat.getColorStateList(applicationContext, R.color.white)
                text = Constants.NOT_IN_BASKET
            }
        } else {
            add_to_basket.apply {
                setBackgroundColor(resources.getColor(R.color.white))
                setTextColor(resources.getColor(R.color.colorPrimary))
                iconTint = ContextCompat.getColorStateList(applicationContext, R.color.gray_tiny)
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

    override fun closeDetails() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }

    // отключаем кнопку добавление в корзину
    private fun disableBasketButton(basketSwitcher: String?) {
        if (basketSwitcher != null) {
            if (basketSwitcher == BASKET_TURN_OFF) {
                add_to_basket.apply {
                    setBackgroundColor(resources.getColor(R.color.white))
                    setTextColor(resources.getColor(R.color.colorPrimary))
                    text = Constants.ALREADY_IN_BASKET
                    isClickable = false
                    isEnabled = false
                }
            }
        }
    }

    // настраиваем тулбар
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

    private fun headerImage() {
        viewPager = findViewById(R.id.view_pager_image)

        val pictures = mutableListOf<String>()
        val one = product!!.picture.urlImage
        val two = product!!.picture.urlImage2
        val three = product!!.picture.urlImage3

        if (two.isEmpty() && three.isEmpty()) {

            // одна фотография товара
            pictures.clear()
            pictures.add(one)
            detailsImageAdapter = DetailsImageAdapter(this, pictures)
        } else if (two.isNotEmpty() && three.isEmpty()) {

            //две фотографии товара
            pictures.clear()
            pictures.add(one)
            pictures.add(two)
            detailsImageAdapter = DetailsImageAdapter(this, pictures)
            makeDotsInViewPager()
        } else if (two.isNotEmpty() && three.isNotEmpty()) {

            // три фотографии товара
            pictures.clear()
            pictures.add(one)
            pictures.add(two)
            pictures.add(three)
            detailsImageAdapter = DetailsImageAdapter(this, pictures)
            makeDotsInViewPager()
        }

        // настраиваем ViewPager
        viewPager.adapter = detailsImageAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    // точки внизу фотографий
    private fun makeDotsInViewPager(){
        tabLayout = findViewById(R.id.image_tab_dots)
        tabLayout.setupWithViewPager(viewPager, true)
    }

    // проверяем видимость продукта
    private fun checkProductVisibility(p: Product) {
        firebaseRepository.loadProductByKey(p.id, object : ProductCallback {
            override fun onCallback(product: Product?) {

                // проверка если продукт удален
                if (product != null) {
                    if (product.visible) {
                        product_availability.text = "Есть в наличии"
                        product_availability.setTextColor(resources.getColor(R.color.green_light))
                    } else {
                        product_availability.text = "Товар скоро будет"
                        product_availability.setTextColor(resources.getColor(R.color.red_light))
                    }
                } else {
                    appbar_layout.visibility = View.GONE
                    details_content.visibility = View.GONE
                    add_to_basket.visibility = View.GONE
                    empty_product.visibility = View.VISIBLE
                    empty_product.text =
                        "Рекомендуем удалить товар из Корзины \n \n Товар снят с продажи"
                }
            }
        })
    }
}