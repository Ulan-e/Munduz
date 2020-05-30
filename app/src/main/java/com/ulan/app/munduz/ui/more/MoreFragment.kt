package com.ulan.app.munduz.ui.more

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.Constants.Companion.HOME_FRAGMENT
import com.ulan.app.munduz.helpers.Constants.Companion.INSTAGRAM_PAGE
import com.ulan.app.munduz.helpers.Constants.Companion.ODNOKLASSNIKI_PAGE
import com.ulan.app.munduz.helpers.Constants.Companion.TELEGRAM_PAGE
import com.ulan.app.munduz.helpers.Constants.Companion.VKONTAKTE_PAGE
import com.ulan.app.munduz.helpers.Constants.Companion.WRITE_TO_US_FRAGMENT
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.more.sections.AboutAppFragment
import com.ulan.app.munduz.ui.more.sections.ContactUsFragment
import com.ulan.app.munduz.ui.more.sections.WriteToUsFragment
import kotlinx.android.synthetic.main.more_layout.*
import javax.inject.Inject

class MoreFragment : BaseFragment(), MoreView {

    @Inject
    lateinit var presenter: MorePresenter

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.more_layout, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setToolbar()

        contacts_us.setOnClickListener {
            presenter.goToContactsUs()
        }

        write_tous_button.setOnClickListener {
            presenter.goToWriteToUsFragment()
        }

        about_app.setOnClickListener(){
            presenter.goToAboutApp()
        }

        instagram.setOnClickListener {
            presenter.socialPageClicked(INSTAGRAM_PAGE)
        }

        vkontakte.setOnClickListener {
            presenter.socialPageClicked(VKONTAKTE_PAGE)
        }

        odnoklassniki.setOnClickListener {
            presenter.socialPageClicked(ODNOKLASSNIKI_PAGE)
        }

        telegram.setOnClickListener {
            presenter.socialPageClicked(TELEGRAM_PAGE)
        }
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.more)
        textToolbar.typeface = Typeface.DEFAULT
        textToolbar.textSize = resources.getDimension(R.dimen.toolbar_title_size)
    }

    override fun showEmptyData() {
        showSnackBar(resources.getString(R.string.error_network_text))
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }

    override fun showContactsUs() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, ContactUsFragment())
            .commit()
    }

    override fun showAboutApp() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, AboutAppFragment())
            .commit()
    }

    override fun showWriteUs() {
        val dialogFragment = WriteToUsFragment()
        dialogFragment.show(activity!!.supportFragmentManager, WRITE_TO_US_FRAGMENT)
    }

    override fun showSocialPage(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onBackPressed(): Boolean {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment(), HOME_FRAGMENT)
            .addToBackStack(null)
            .commit()
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
        return true
    }

}