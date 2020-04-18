package com.ulan.app.munduz.ui.more

import javax.inject.Inject

class MorePresenterImpl : MorePresenter {

    private var mView: MoreView?

    @Inject
    constructor(mView: MoreView) {
        this.mView = mView
    }

    override fun setToolbar() {
        mView?.showToolbar()
    }

    override fun goToContactsUs() {
        mView?.showContactsUs()
    }

    override fun goToAboutApp() {
        mView?.showAboutApp()
    }

    override fun goToWriteToUsFragment() {
        mView?.showWriteUs()
    }

    override fun socialPageClicked(siteUrl: String) {
        mView?.showSocialPage(siteUrl)
    }

    override fun detachView() {
        mView = null
    }
}