package com.ulan.app.munduz.ui.fragments.more

import com.ulan.app.munduz.ui.base.BasePresenter

interface MorePresenter : BasePresenter {

    fun goToContactsUs()

    fun goToAboutApp()

    fun goToWriteToUsFragment()

    fun socialPageClicked(siteUrl: String)

}