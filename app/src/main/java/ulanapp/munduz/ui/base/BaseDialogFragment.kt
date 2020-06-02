package ulanapp.munduz.ui.base

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.purchase_layout.*

abstract class BaseDialogFragment: DaggerAppCompatDialogFragment() {

    override fun onAttach(activity: Activity) {
        AndroidSupportInjection.inject(this)
        super.onAttach(activity)
    }

    protected fun showSnackBar(view: View, text: String){
        val snack = Snackbar.make(root_purchase_layout, text, Snackbar.LENGTH_SHORT)
        snack.show()
    }
}