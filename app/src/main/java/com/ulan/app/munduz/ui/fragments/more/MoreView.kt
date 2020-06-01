package com.ulan.app.munduz.ui.fragments.more

import com.ulan.app.munduz.ui.base.BaseView

interface MoreView : BaseView{

    fun showContactsUs()

    fun showAboutApp()

    fun showWriteUs()

    fun showSocialPage(url: String)

}
