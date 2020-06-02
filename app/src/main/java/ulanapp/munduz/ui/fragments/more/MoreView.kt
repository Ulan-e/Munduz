package ulanapp.munduz.ui.fragments.more

import ulanapp.munduz.ui.base.BaseView

interface MoreView : BaseView {

    fun showContactsUs()

    fun showAboutApp()

    fun showWriteUs()

    fun showSocialPage(url: String)

}
