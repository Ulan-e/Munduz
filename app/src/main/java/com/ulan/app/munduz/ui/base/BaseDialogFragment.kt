package com.ulan.app.munduz.ui.base

import android.app.Activity
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatDialogFragment

open class BaseDialogFragment: DaggerAppCompatDialogFragment() {

    override fun onAttach(activity: Activity) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }
}