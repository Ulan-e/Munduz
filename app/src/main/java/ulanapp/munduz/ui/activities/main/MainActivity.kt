package ulanapp.munduz.ui.activities.main

import android.app.Activity
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus.DOWNLOADED
import com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import ulanapp.munduz.R
import ulanapp.munduz.helpers.Constants.Companion.BASKET_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.CATALOG_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.EXTRA_OPEN_BASKET
import ulanapp.munduz.helpers.Constants.Companion.FAVORITE_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.MORE_FRAGMENT
import ulanapp.munduz.helpers.Constants.Companion.OPEN_BASKET_ARG
import ulanapp.munduz.helpers.Constants.Companion.TAG
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

    private lateinit var appUpdateManager: AppUpdateManager

    companion object {
        const val REQUEST_UPDATE_CODE = 13
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_UPDATE_CODE
                    )
                    Log.e(TAG, "Update success")
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            } else if (appUpdateInfo.installStatus() == DOWNLOADED) {
                popupMessageForCompleteUpdate()
            } else {
                Log.e(TAG, "checkForAppUpdateAvailability: something else")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val title = intent.getStringExtra(EXTRA_OPEN_BASKET)

        presenter.bindView(this)

        // проверка какой фрагмент открыть
        if (title == OPEN_BASKET_ARG) {
            initBottomNav(R.id.basket)
        } else {
            initBottomNav(R.id.home)
        }

        // клик на поиск продукта
        button_click.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    override fun initBottomNav(id: Int) {
        bottom_navigation_menu.setOnNavigationItemSelectedListener(itemSelectedListener)
        bottom_navigation_menu.selectedItemId = id
    }

    override fun showFragment(fragment: BaseFragment, title: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, title)
            .addToBackStack(null)
            .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_UPDATE_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                Log.e(TAG, "Update flow failed! Result code: $resultCode")
            }
        }
    }

    override fun onBackPressed() {
        onBackPressedInFragments()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView(this)
    }

    private fun onBackPressedInFragments() {
        val fragments = supportFragmentManager.fragments
        for (fragment: Fragment in fragments) {
            if (fragment is BaseFragment) {
                fragment.onBackPressed()
            }
        }
    }

    // показываем попап обновления завершено
    private fun popupMessageForCompleteUpdate() {
        val snackMessage = Snackbar.make(
            main_activity_root,
            resources.getString(R.string.app_updated),
            Snackbar.LENGTH_INDEFINITE
        )

        // рестарт приложения
        snackMessage.setAction(resources.getString(R.string.restart)) {
            appUpdateManager.completeUpdate()
        }
        snackMessage.setActionTextColor(resources.getColor(R.color.green_dark))
        snackMessage.show()
    }

    private var itemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    presenter.addFragment(HomeFragment(), HOME_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.catalog -> {
                    presenter.addFragment(CatalogFragment(), CATALOG_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.basket -> {
                    presenter.addFragment(BasketFragment(), BASKET_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    presenter.addFragment(FavoriteFragment(), FAVORITE_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.more -> {
                    presenter.addFragment(MoreFragment(), MORE_FRAGMENT)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
}