package ulanapp.munduz.ui.activities.main

import ulanapp.munduz.ui.base.BaseFragment

interface MainView {

    fun initBottomNav(id: Int)

    fun showFragment(fragment: BaseFragment, title: String)

}