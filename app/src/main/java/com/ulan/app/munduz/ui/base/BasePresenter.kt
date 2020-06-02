package com.ulan.app.munduz.ui.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.annotation.Nullable

abstract class Presenter<V> {

    @Nullable
    private var view: V? = null

    protected fun getView(): V? {
        Log.d("ulanbek", "getView() $view")
        return view
    }

    @CallSuper
    open fun bindView(@NonNull view: V) {
        this.view = view
        Log.d("ulanbek", "bindView() $view")
    }

    @CallSuper
    open fun unbindView(@NonNull view: V) {
        if (this.view == view) {
            this.view = null
        } else {
            throw IllegalStateException("Unexpected view")
        }
        Log.d("ulanbek", "unbindView() $view")
    }
}