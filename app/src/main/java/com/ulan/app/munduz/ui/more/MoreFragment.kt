package com.ulan.app.munduz.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.base.BaseFragment
import kotlinx.android.synthetic.main.more_layout.*

class MoreFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.more_layout, container, false)
    }

    private fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.more)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbar()

        instagram.setOnClickListener{
            openInstagram()
        }

        facebook.setOnClickListener{
            openFacebook()
        }

        odnoklassniki.setOnClickListener{
            openOdnoklassniki()
        }
    }

    companion object{
        fun newInstance(): MoreFragment {
            val args: Bundle = Bundle()
            val fragment = MoreFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun openInstagram(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/munduz.ru"))
        startActivity(intent)
    }

    private fun openOdnoklassniki(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ok.ru/profile/581176986653"))
        startActivity(intent)
    }

    private fun openFacebook(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/profile.php?id=100008205792318"))
        startActivity(intent)
    }

}