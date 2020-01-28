package com.ulan.app.munduz.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.search.SearchActivity
import com.ulan.app.munduz.ui.base.BaseActivity
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.catalog.CatalogFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.liked.LikedFragment
import com.ulan.app.munduz.ui.more.MoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenterImpl(this)

        button_click.setOnClickListener{
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }

        initBottomNav()
    }

    override fun initToolbar() {

    }

    override fun initBottomNav() {
        bottom_navigation_menu.setOnNavigationItemSelectedListener(itemSelectedListener)
        bottom_navigation_menu.selectedItemId = R.id.home
    }

    private var itemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            menuItem ->
        when (menuItem.itemId) {
            R.id.home -> {
                val homeFragment = HomeFragment.newInstance()
                mPresenter.addFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.catalog -> {
                val catalogFragment = CatalogFragment.newInstance()
                mPresenter.addFragment(catalogFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.basket -> {
                val basketFragment = LikedFragment.newInstance()
                mPresenter.addFragment(basketFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.more -> {
                val moreFragment =
                    MoreFragment.newInstance()
                mPresenter.addFragment(moreFragment)
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



}
