package com.ulan.app.munduz.ui.base

import android.content.Context
import com.ulan.app.munduz.listeners.OnBackPressedListener
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment(), OnBackPressedListener {

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}