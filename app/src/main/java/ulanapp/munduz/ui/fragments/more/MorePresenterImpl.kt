package ulanapp.munduz.ui.fragments.more

import ulanapp.munduz.ui.base.BasePresenter
import javax.inject.Inject

class MorePresenterImpl @Inject constructor() : BasePresenter<MoreView>(),MorePresenter {

    override fun goToContactsUs() {
        getView()?.showContactsUs()
    }

    override fun goToAboutApp() {
        getView()?.showAboutApp()
    }

    override fun goToWriteToUsFragment() {
        getView()?.showWriteUs()
    }

    override fun socialPageClicked(siteUrl: String) {
        getView()?.showSocialPage(siteUrl)
    }

}