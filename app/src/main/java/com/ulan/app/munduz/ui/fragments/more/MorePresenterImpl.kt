package com.ulan.app.munduz.ui.fragments.more

import javax.inject.Inject

class MorePresenterImpl : MorePresenter {

    private var view: MoreView?

    @Inject
    constructor(mView: MoreView) {
        this.view = mView
    }

    override fun goToContactsUs() {
        view?.showContactsUs()
    }

    override fun goToAboutApp() {
        view?.showAboutApp()
    }

    override fun goToWriteToUsFragment() {
        view?.showWriteUs()
    }

    override fun socialPageClicked(siteUrl: String) {
        view?.showSocialPage(siteUrl)
    }

    override fun detachView() {
        view = null
    }
}