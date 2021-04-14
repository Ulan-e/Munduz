package ulanapp.munduz.ui.activities.main

import ulanapp.munduz.ui.base.BaseFragment

interface MainPresenter {

    fun addFragment(fragment: BaseFragment, title: String)
}