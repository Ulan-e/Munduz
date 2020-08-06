package ulanapp.munduz.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import ulanapp.munduz.R
import ulanapp.munduz.helpers.Constants.Companion.CATALOG_FRAGMENT
import ulanapp.munduz.helpers.isNetworkAvailable
import ulanapp.munduz.interfaces.OnBackPressedListener
import ulanapp.munduz.ui.fragments.catalog.CatalogFragment

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
        val titleToolbar = toolbar?.findViewById<TextView>(R.id.main_toolbar_text)

        enableNavigation(toolbar, withBackButton)
        setTitle(toolbar, title)
        changeTitleFont(titleToolbar, isAppName)

    }

    private fun enableNavigation(toolbar: Toolbar, withBackButton: Boolean) {
        if (withBackButton) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            toolbar.setNavigationOnClickListener {
                activity!!.supportFragmentManager.popBackStack()
            }
        } else {
            toolbar.navigationIcon = null
        }
    }

    private fun setTitle(toolbar: Toolbar?, title: String) {
        val textToolbar = toolbar?.findViewById<TextView>(R.id.main_toolbar_text)
        val emptySpaces = "       "
        textToolbar?.text = title + emptySpaces
    }

    private fun changeTitleFont(titleToolbar: TextView?, isAppName: Boolean) {
        if (isAppName) {
            activity!!.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
            val typeface = ResourcesCompat.getFont(activity!!, R.font.forte)
            titleToolbar?.typeface = typeface
            titleToolbar?.textSize = resources.getDimension(R.dimen.toolbar_app_title_size)
        } else {
            activity!!.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
            val typeface = ResourcesCompat.getFont(activity!!, R.font.calibri)
            titleToolbar?.typeface = typeface
            titleToolbar?.textSize = resources.getDimension(R.dimen.toolbar_title_size)
        }
    }

    protected fun checkInternetConnection(){
        if(!isNetworkAvailable(activity!!)){
            Toast.makeText(activity!!, "Нет подключения к Интернету", Toast.LENGTH_SHORT).show()
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