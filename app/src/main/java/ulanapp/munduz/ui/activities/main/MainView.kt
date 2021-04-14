package ulanapp.munduz.ui.activities.main

import ulanapp.munduz.ui.base.BaseFragment

interface MainView {

    // настройки нижнего меню
    fun initBottomNav(id: Int)

    // запуск фрагмента
    fun showFragment(fragment: BaseFragment, title: String)
}