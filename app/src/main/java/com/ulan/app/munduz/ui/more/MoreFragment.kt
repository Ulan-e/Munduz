package com.ulan.app.munduz.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.Constants.Companion.FACEBOOK_PAGE
import com.ulan.app.munduz.helpers.Constants.Companion.INSTAGRAM_PAGE
import com.ulan.app.munduz.helpers.Constants.Companion.ODNOKLASSNIKI_PAGE
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.main.MainActivity
import com.ulan.app.munduz.ui.more.sections.ContactUsFragment
import com.ulan.app.munduz.ui.more.sections.WriteToUsFragment
import kotlinx.android.synthetic.main.more_layout.*
import javax.inject.Inject

class MoreFragment : BaseFragment(), MoreView {

    @Inject
    lateinit var mPresenter: MorePresenter

    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.more_layout, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPresenter.setToolbar()

        contacts_us.setOnClickListener {
            mPresenter.goToContactsUs()
        }

        write_tous_button.setOnClickListener {
            mPresenter.goToWriteToUsFragment()
        }

        instagram.setOnClickListener {
            mPresenter.instagramClicked()
        }

        facebook.setOnClickListener {
            mPresenter.facebookClicked()
        }

        odnoklassniki.setOnClickListener {
            mPresenter.odnoklassnikiClicked()
        }
    }

    override fun showToolbar() {
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.navigationIcon = null
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.more)
    }

    override fun showEmptyData() {
        showSnackBar(resources.getString(R.string.error_open_social_page))
    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(mView, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }

    override fun showContactsUs() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, ContactUsFragment())
            .commit()
    }

    override fun showLangChange() {
        TODO("not implemented")
    }

    override fun showWriteUs() {
        val dialogFragment = WriteToUsFragment()
        dialogFragment.show(activity!!.supportFragmentManager, "dialogWriteUs")
    }

    override fun showInstagramPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_PAGE))
        startActivity(intent)
    }

    override fun showOdnoklassnikiPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ODNOKLASSNIKI_PAGE))
        startActivity(intent)
    }

    override fun showFacebookPage() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_PAGE))
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onBackPressed(): Boolean {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment.newInstance(), "home")
            .addToBackStack(null)
            .commit()
        val bottomNav = activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        bottomNav.selectedItemId = R.id.home
        return true
    }

    companion object {
        fun newInstance(): MoreFragment {
            val args = Bundle()
            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

}