package com.ulan.app.munduz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ulan.app.munduz.R

class MainActivity : AppCompatActivity() {

    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBottomNavigationView = findViewById(R.id.bottom_navigation_menu)
        mToolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(mToolbar)
        mBottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener)
        mBottomNavigationView.selectedItemId = R.id.home
    }

    private var itemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{
            menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment, "title1")
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.catalog -> {
                    val fragment = CatalogFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment, "title2")
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.basket -> {
                    val fragment = BasketFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment, "title3")
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    val fragment = MoreFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment, "title4")
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
        false
        }

}
