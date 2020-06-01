package com.ulan.app.munduz.ui.fragments.more.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ulan.app.munduz.R
import com.ulan.app.munduz.helpers.Constants.Companion.EMPTY_SPACE
import com.ulan.app.munduz.ui.base.BaseFragment

class AboutAppFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = resources.getString(R.string.about_app) + EMPTY_SPACE
        showToolbarTitle(false, title)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

}
