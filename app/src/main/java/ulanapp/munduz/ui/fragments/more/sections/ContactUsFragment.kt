package ulanapp.munduz.ui.fragments.more.sections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ulanapp.munduz.R
import ulanapp.munduz.ui.base.BaseFragment

class ContactUsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_us, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToolbarTitle(
            withBackButton = true,
            isAppName = false,
            title = resources.getString(R.string.contacts_us)
        )
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}