package ulanapp.munduz.ui.activities.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import ulanapp.munduz.R
import ulanapp.munduz.helpers.Constants
import ulanapp.munduz.helpers.Constants.Companion.BASKET_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.CATALOG_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_OPEN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.FAVORITE_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.MORE_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.OPEN_BASKET_ARG
import ulanapp.munduz.ui.activities.search.SearchActivity
import ulanapp.munduz.ui.base.BaseActivity
import ulanapp.munduz.ui.base.BaseFragment
import ulanapp.munduz.ui.fragments.basket.BasketFragment
import ulanapp.munduz.ui.fragments.catalog.CatalogFragment
import ulanapp.munduz.ui.fragments.favorite.FavoriteFragment
import ulanapp.munduz.ui.fragments.home.HomeFragment
import ulanapp.munduz.ui.fragments.more.MoreFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenterImpl

    lateinit var appUpdateManager: AppUpdateManager

    companion object{
        const val REQUEST_UPDATE_CODE = 13
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appUpdateManager = AppUpdateManagerFactory.create(this)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_UPDATE_CODE){
            if(resultCode != Activity.RESULT_OK){
                Log.e(Constants.TAG, "Update flow failed! Result code: $resultCode")
            }
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

    override fun onResume() {
        super.onResume()
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if(appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                
            }
        }
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