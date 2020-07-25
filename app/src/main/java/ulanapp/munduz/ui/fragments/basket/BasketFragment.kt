package ulanapp.munduz.ui.fragments.basket


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ulanapp.munduz.data.models.Product
import kotlinx.android.synthetic.main.basket_layout.*
import ulanapp.munduz.R
import ulanapp.munduz.data.models.Picture
import ulanapp.munduz.data.models.PurchaseEntity
import ulanapp.munduz.data.room.repository.PurchasesRepository
import ulanapp.munduz.helpers.Constants.Companion.BASKET_TURN_OFF
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_AMOUNT_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PRODUCT_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_PURCHASES_BUY_ARG
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_TURN_OFF_ADD_BASKET
import ulanapp.munduz.helpers.RUBLE
import ulanapp.munduz.interfaces.OnChangeSumListener
import ulanapp.munduz.interfaces.OnItemBasketClickListener
import ulanapp.munduz.ui.activities.details.DetailsActivity
import ulanapp.munduz.ui.activities.orders.OrdersActivity
import ulanapp.munduz.ui.adapter.BasketAdapter
import ulanapp.munduz.ui.base.BaseFragment
import javax.inject.Inject

class BasketFragment : BaseFragment(), BasketView, OnItemBasketClickListener, OnChangeSumListener {

    @Inject
    lateinit var presenter: BasketPresenterImpl

    @Inject
    lateinit var adapter: BasketAdapter

    @Inject
    lateinit var purchasesRepository: PurchasesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.basket_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showToolbarTitle(
            withBackButton = false,
            isAppName = false,
            title = resources.getString(R.string.basket)
        )

        presenter.bindView(this)
        presenter.setListener(this)
        presenter.loadProducts()

        purchase_all.setOnClickListener {
            presenter.purchaseButtonClicked()
        }

        open_catalog.setOnClickListener {
            presenter.goToHomeButtonClicked()
        }
    }

    override fun showEmptyData() {
        empty_basket.visibility = View.VISIBLE
    }

    override fun showProducts(purchases: MutableList<PurchaseEntity>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        adapter.apply {
            setProducts(purchases)
            setRepository(purchasesRepository)
        }.setListener(this)

        basket_recycler_view.layoutManager = layoutManager
        basket_recycler_view.adapter = adapter
    }

    override fun purchaseAll(purchases: MutableList<PurchaseEntity>) {
        val intent = Intent(activity!!, OrdersActivity::class.java)
        intent.putParcelableArrayListExtra(EXTRA_PURCHASES_BUY_ARG, ArrayList(purchases))
        startActivity(intent)
    }

    override fun showGoToHome() {
        goToHome()
    }

    override fun hidePurchaseButton() {
        sum_of_purchase.visibility = View.GONE
        purchase_all.visibility = View.GONE
    }

    override fun showPurchaseButton() {
        sum_of_purchase.visibility = View.VISIBLE
        purchase_all.visibility = View.VISIBLE
    }

    override fun onItemClick(purchase: PurchaseEntity) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(EXTRA_PRODUCT_ARG, convertPurchaseToProduct(purchase))
        intent.putExtra(EXTRA_TURN_OFF_ADD_BASKET, BASKET_TURN_OFF)
        startActivity(intent)
    }

    override fun onBackPressed(): Boolean {
        goToHome()
        return true
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }

    override fun onAmountChanged(amount: Int) {
        if (amount > 0) {
            val amountText = activity!!.resources.getString(R.string.purchases_amount)
            sum_of_purchase.text = amountText + amount.toString() + RUBLE
        } else {
            hidePurchaseButton()
            showEmptyData()
        }
    }

    private fun convertPurchaseToProduct(purchase: PurchaseEntity): Product {
        val product = Product()
        val pictures = Picture()
        product.id = purchase.id
        product.name = purchase.name
        product.desc = purchase.desc
        product.cost = purchase.price
        product.priceFor = purchase.perPrice
        pictures.urlImage = purchase.picture.urlImage
        pictures.urlImage2 = purchase.picture.urlImage2
        pictures.urlImage3 = purchase.picture.urlImage3
        product.picture = pictures
        product.category = purchase.category
        return product
    }

}