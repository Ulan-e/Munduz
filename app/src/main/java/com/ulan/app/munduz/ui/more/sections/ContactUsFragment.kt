package com.ulan.app.munduz.ui.more.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.ulan.app.munduz.R
import com.ulan.app.munduz.ui.base.BaseFragment
import kotlinx.android.synthetic.main.more_layout.*

class ContactUsFragment: BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.contacts_us, container, false)
        showToolbar()
        return view
    }

    private fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<Toolbar>(R.id.main_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = resources.getString(R.string.contacts_us)
        toolbar.setNavigationOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

}