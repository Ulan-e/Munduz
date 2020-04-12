package com.ulan.app.munduz.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.basket.BasketFragment
import com.ulan.app.munduz.ui.catalog.CatalogFragment
import com.ulan.app.munduz.ui.favorite.FavoriteFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.more.MoreFragment
import com.ulan.app.munduz.ui.search.SearchActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = BasketFragment.newInstance()
        val mTitle = intent.getStringExtra("ulan")
        if (mTitle == "basket") {
            initBottomNav(R.id.basket)
            Log.d("ulanbek", "go to basket")
        }else{
            initBottomNav(R.id.home)
            Log.d("ulanbek", "go to home")
        }

        button_click.setOnClickListener{
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
                    val homeFragment = HomeFragment.newInstance()
                    mPresenter.addFragment(homeFragment, "homef")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.catalog -> {
                    val catalogFragment = CatalogFragment.newInstance()
                    mPresenter.addFragment(catalogFragment, "catalogf")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.basket -> {
                    val basketFragment = BasketFragment.newInstance()
                    mPresenter.addFragment(basketFragment, "basketf")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    val favoriteFragment = FavoriteFragment.newInstance()
                    mPresenter.addFragment(favoriteFragment, "favoritef")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    val moreFragment =
                        MoreFragment.newInstance()
                    mPresenter.addFragment(moreFragment, "moref")
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
}
