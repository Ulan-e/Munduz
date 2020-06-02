package ulanapp.munduz.ui.activities.main

import ulanapp.munduz.ui.base.BaseFragment
import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class MainPresenterImpl @Inject constructor() : BasePresenter<MainView>(), MainPresenter {

    override fun addFragment(fragment: BaseFragment, title: String) {
        getView()?.showFragment(fragment, title)
    }

}