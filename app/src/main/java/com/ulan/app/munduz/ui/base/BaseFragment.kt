package com.ulan.app.munduz.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.interfaces.OnBackPressedListener
import com.ulan.app.munduz.ui.fragments.home.HomeFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), OnBackPressedListener {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    protected fun showToolbarTitle(isAppName: Boolean, title: String) {
        activity!!.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity!!.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null

        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = title

        if (isAppName) {
            val typeface = ResourcesCompat.getFont(activity!!, R.font.forte)
            textToolbar.typeface = typeface
            textToolbar.textSize = resources.getDimension(R.dimen.toolbar_app_title_size)
        } else {
            val typeface = ResourcesCompat.getFont(activity!!, R.font.calibri)
            textToolbar.typeface = typeface
            textToolbar.textSize = resources.getDimension(R.dimen.toolbar_title_size)
        }
    }

    protected fun goToHome() {
        val bottomNav =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment(), HOME_FRAGMENT)
            .addToBackStack(null)
            .commit()
    }

}