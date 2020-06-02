package com.ulan.app.munduz.ui.fragments.filtered

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.adapter.ProductsAdapter
import com.ulan.app.munduz.data.room.repository.FavoritesRepository
import com.ulan.app.munduz.data.room.repository.PurchasesRepository
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.CATEGORY_ARG
import com.ulan.app.munduz.helpers.Constants.Companion.EMPTY_SPACE
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.helpers.isNetworkAvailable
import com.ulan.app.munduz.interfaces.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.activities.details.DetailsActivity
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.filtered_layout.*
import javax.inject.Inject

class FilteredFragment : BaseFragment(), FilteredView, OnItemClickListener {

    @Inject
    lateinit var presenter: FilteredPresenterImpl

    @Inject
    lateinit var adapter: ProductsAdapter

    @Inject
    lateinit var firebaseRepository: FavoritesRepository

    @Inject
    lateinit var purchasesRepository: PurchasesRepository

    private lateinit var titleCategory: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filtered_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleCategory = arguments!!.getString(CATEGORY_ARG)
        val title = "$titleCategory $EMPTY_SPACE"
        showToolbarTitle(false, title)

        presenter.bindView(this)
        presenter.loadProductsByCategory(titleCategory)
    }

    override fun isNetworkOn(): Boolean {
        return isNetworkAvailable(activity!!.applicationContext)
    }

    override fun showErrorNetwork() {
        error_layout.visibility = View.VISIBLE
        error_network_button.setOnClickListener {
            val fragment = activity!!.supportFragmentManager.findFragmentByTag(HOME_FRAGMENT)
            val ft = activity!!.supportFragmentManager.beginTransaction()
            ft.detach(fragment!!)
            ft.attach(fragment)
            ft.commit()
        }
    }

    override fun showEmptyData() {
        empty_filter_catalog.visibility = View.VISIBLE
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        filter_recycler_view.layoutManager = layoutManager
        adapter.setProducts(products)
        adapter.setRepositories(firebaseRepository, purchasesRepository)
        filter_recycler_view.adapter = adapter
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.EXTRA_PRODUCT_ARG, product)
        intent.putExtra(Constants.EXTRA_TURN_OFF_ADD_BASKET, Constants.BASKET_TURN_ON)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    companion object {
        fun newInstance(category: String): FilteredFragment {
            val fragment = FilteredFragment()
            val args = Bundle()
            args.putString(CATEGORY_ARG, category)
            fragment.arguments = args
            return fragment
        }
    }
}