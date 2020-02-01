package com.ulan.app.munduz.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.CatalogAdapter
import com.ulan.app.munduz.helpers.listeners.OnCategoryClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.filtered.FilteredFragment
import kotlinx.android.synthetic.main.catalog_layout.*
import javax.inject.Inject

class CatalogFragment : BaseFragment(), CatalogView, OnCategoryClickListener {

    @Inject
    lateinit var mPresenter: CatalogPresenter

    @Inject
    lateinit var mAdapter: CatalogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.catalog_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.setToolbar()
        mPresenter.loadCatalog()
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.catalog)
    }

    override fun showCatalog(catalog: MutableList<String>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        catalog_recycler_view.layoutManager = layoutManager
        mAdapter.setCatalogs(catalog)
        catalog_recycler_view.adapter = mAdapter
    }

    override fun onCategoryClick(category: String) {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, FilteredFragment.newInstance(category))
            .addToBackStack(null)
            .commit()
    }

    override fun showNoCatalog() {
        empty_catalog.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): CatalogFragment {
            val args = Bundle()
            val fragment = CatalogFragment()
            fragment.arguments = args
            return fragment
        }

    }
}
