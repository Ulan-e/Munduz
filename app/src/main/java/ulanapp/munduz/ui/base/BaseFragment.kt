package ulanapp.munduz.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import ulanapp.munduz.R
import ulanapp.munduz.helpers.Constants.Companion.CATALOG_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import ulanapp.munduz.interfaces.OnBackPressedListener
import ulanapp.munduz.ui.fragments.catalog.CatalogFragment
import ulanapp.munduz.ui.fragments.home.HomeFragment

abstract class BaseFragment : DaggerFragment(), OnBackPressedListener {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    protected fun showToolbarTitle(withBackButton: Boolean, isAppName: Boolean, title: String) {
        val toolbar = activity!!.findViewById<Toolbar>(R.id.main_toolbar)
        if (withBackButton) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            toolbar.setNavigationOnClickListener{
                activity!!.supportFragmentManager.popBackStack()
            }
        } else {
            toolbar.navigationIcon = null
        }

        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = title

        if (isAppName) {
            activity!!.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
            val typeface = ResourcesCompat.getFont(activity!!, R.font.forte)
            textToolbar.typeface = typeface
            textToolbar.textSize = resources.getDimension(R.dimen.toolbar_app_title_size)
        } else {
            activity!!.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
            val typeface = ResourcesCompat.getFont(activity!!, R.font.calibri)
            textToolbar.typeface = typeface
            textToolbar.textSize = resources.getDimension(R.dimen.toolbar_title_size)
        }
    }

    protected fun goToHome() {
        val bottomNav =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.catalog
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, CatalogFragment(), CATALOG_FRAGMENT)
            .addToBackStack(null)
            .commit()
    }

}