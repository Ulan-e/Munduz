package com.ulan.app.munduz.ui.more

class MorePresenterImpl : MorePresenter {

    private var mView: MoreView?

    constructor(mView: MoreView) {
        this.mView = mView
    }

    override fun goToContactsUs() {
        mView?.showContactsUs()
    }

    override fun goToLangChange() {
        mView?.showLangChange()
    }

    override fun goToAboutApp() {
        mView?.showAboutApp()
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