package ulanapp.munduz.ui.base

import androidx.annotation.CallSuper
import androidx.annotation.NonNull
import androidx.annotation.Nullable

abstract class BasePresenter<V> {

    @Nullable
    private var view: V? = null

    protected fun getView(): V? {
        return view
    }

    @CallSuper
    open fun bindView(@NonNull view: V) {
        this.view = view
    }

    @CallSuper
    open fun unbindView(@NonNull view: V) {
        if (this.view == view) {
            this.view = null
        } else {
            throw IllegalStateException("Unexpected view")
        }
    }

}