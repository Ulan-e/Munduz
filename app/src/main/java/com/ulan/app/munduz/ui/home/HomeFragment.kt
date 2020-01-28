package com.ulan.app.munduz.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.repository.RepositoryImpl
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.home_layout.*

class HomeFragment : BaseFragment(), HomeView,
    OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mRepository: Repository
    private lateinit var mPresenter: HomePresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.home_layout, container, false)
        recyclerView = view!!.findViewById<RecyclerView>(R.id.home_recycler_view)
        showToolbar()
        return view
    }

    private fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        activity.supportActionBar?.hide()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRepository = RepositoryImpl(activity!!.applicationContext)
        mPresenter = HomePresenterImpl(mRepository)
        mPresenter.attachView(this)
        mPresenter.loadProducts()
    }

    override fun showProgress() {
        home_progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        home_progress_bar.visibility = View.GONE
    }

    override fun showNoProducts() {
        showNoProducts()
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        recyclerView.layoutManager = layoutManager
        val adapter  = ProductAdapter(activity!!, products ,this)
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(PRODUCT_ARG, product)
        startActivity(intent)

    }
}