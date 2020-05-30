package com.ulan.app.munduz.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.CatalogAdapter
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.listeners.OnCategoryClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.filtered.FilteredFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import kotlinx.android.synthetic.main.catalog_layout.*
import javax.inject.Inject

class CatalogFragment : BaseFragment(), CatalogView, OnCategoryClickListener {

    @Inject
    lateinit var presenter: CatalogPresenter

    @Inject
    lateinit var adapter: CatalogAdapter

    private var images = intArrayOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.catalog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setToolbar()
        val catalog = activity!!.applicationContext.resources.getStringArray(R.array.category)
        images = intArrayOf(
            R.mipmap.grocery, R.mipmap.napitki,
            R.mipmap.postel, R.mipmap.dishes,
            R.mipmap.kazan, R.mipmap.chemodany,
            R.mipmap.beshik, R.mipmap.odezhda,
            R.mipmap.sredstvo
        )
        presenter.setCatalog(catalog.toMutableList())
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        val typeface = ResourcesCompat.getFont(activity, R.font.forte)
        textToolbar.typeface = typeface
        textToolbar.text = resources.getString(R.string.app_name)
        textToolbar.textSize = resources.getDimension(R.dimen.toolbar_app_title_size)
    }

    override fun showCatalog(catalog: MutableList<String>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        catalog_recycler_view.layoutManager = layoutManager
        catalog_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.HORIZONTAL
            )
        )
        adapter.setCatalogs(catalog.toMutableList())
        adapter.setImages(images)
        catalog_recycler_view.adapter = adapter
    }

    override fun onCategoryClick(category: String) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, FilteredFragment.newInstance(category), "filterf")
            .addToBackStack(null)
            .commit()
    }

    override fun showEmptyData() {
        empty_catalog.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onBackPressed(): Boolean {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment(), HOME_FRAGMENT)
            .addToBackStack(null)
            .commit()
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
        return true
    }
}
