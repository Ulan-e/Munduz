package com.ulan.app.munduz.ui.catalog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.CatalogAdapter
import com.ulan.app.munduz.data.repository.Repository
import com.ulan.app.munduz.data.repository.RepositoryImpl
import com.ulan.app.munduz.helpers.Constants.Companion.CATEGORY_ARG
import com.ulan.app.munduz.listeners.OnCategoryClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.filtered.FilteredActivity

class CatalogFragment: BaseFragment(), CatalogView, OnCategoryClickListener {

    private lateinit var mPresenter: CatalogPresenter
    private lateinit var mRepository: Repository
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.catalog_layout, container, false)
        recyclerView = view!!.findViewById<RecyclerView>(R.id.catalog_recycler_view)
        showToolbar()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRepository = RepositoryImpl(activity!!)
        mPresenter= CatalogPresenterImpl(this, mRepository)
        mPresenter.loadCatalog()
    }

    private fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.catalog)
    }

    override fun showCatalog(catalogs: MutableList<String>) {
        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        val adapter = CatalogAdapter(activity!!, catalogs, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun showNoCatalog(text: String) {
        TODO("not implemented")
    }

    companion object{
        fun newInstance(): CatalogFragment {
            val args = Bundle()
            val fragment = CatalogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCategoryClick(category: String) {
        val intent = Intent(activity, FilteredActivity::class.java)
        intent.putExtra(CATEGORY_ARG, category)
        startActivity(intent)
    }
}
