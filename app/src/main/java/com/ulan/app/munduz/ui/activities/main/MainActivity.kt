package com.ulan.app.munduz.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.Constants.Companion.BASKET_FRAGMENT
import com.ulan.app.munduz.helpers.Constants.Companion.CATALOG_FRAGMENT
import com.ulan.app.munduz.helpers.Constants.Companion.EXTRA_OPEN_BASKET
import com.ulan.app.munduz.helpers.Constants.Companion.FAVORITE_FRAGMENT
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.helpers.Constants.Companion.MORE_FRAGMENT
import com.ulan.app.munduz.helpers.Constants.Companion.OPEN_BASKET_ARG
import com.ulan.app.munduz.ui.activities.search.SearchActivity
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.fragments.basket.BasketFragment
import com.ulan.app.munduz.ui.fragments.catalog.CatalogFragment
import com.ulan.app.munduz.ui.fragments.favorite.FavoriteFragment
import com.ulan.app.munduz.ui.fragments.home.HomeFragment
import com.ulan.app.munduz.ui.fragments.more.MoreFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = intent.getStringExtra(EXTRA_OPEN_BASKET)

        presenter.bindView(this)

        if (title == OPEN_BASKET_ARG) {
            initBottomNav(R.id.basket)
        } else {
            initBottomNav(R.id.home)
        }

        button_click.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    override fun initBottomNav(id: Int) {
        bottom_navigation_menu.setOnNavigationItemSelectedListener(itemSelectedListener)
        bottom_navigation_menu.selectedItemId = id
    }

    private var itemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val homeFragment = HomeFragment()
                    presenter.addFragment(homeFragment, HOME_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.catalog -> {
                    val catalogFragment = CatalogFragment()
                    presenter.addFragment(catalogFragment, CATALOG_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.basket -> {
                    val basketFragment = BasketFragment()
                    presenter.addFragment(basketFragment, BASKET_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    val favoriteFragment = FavoriteFragment()
                    presenter.addFragment(favoriteFragment, FAVORITE_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    val moreFragment = MoreFragment()
                    presenter.addFragment(moreFragment, MORE_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun showFragment(fragment: BaseFragment, title: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, title)
            .addToBackStack(null)
            .commit()

    }

    override fun onBackPressed() {
        onBackPressedInFragments()
        super.onBackPressed()
    }

    private fun onBackPressedInFragments() {
        var fragments = supportFragmentManager.fragments
        for (fragment: Fragment in fragments) {
            if (fragment is BaseFragment) {
                fragment.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }
}
