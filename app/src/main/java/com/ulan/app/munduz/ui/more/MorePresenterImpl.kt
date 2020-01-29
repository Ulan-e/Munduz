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

    override fun goToLangChange() {
        mView?.showLangChange()
    }

    override fun goToWriteToUsFragment() {
        mView?.showWriteUs()
    }

    override fun instagramClicked() {
        mView?.showInstagramPage()
    }

    override fun odnoklassnikiClicked() {
        mView?.showOdnoklassnikiPage()
    }

    override fun facebookClicked() {
        mView?.showFacebookPage()
    }

    override fun detachView() {
        mView = null
    }
}