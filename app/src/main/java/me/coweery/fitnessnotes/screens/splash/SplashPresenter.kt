package me.coweery.fitnessnotes.screens.splash

import me.coweery.fitnessnotes.data.login.CredentialsStorage
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val credentialsStorage: CredentialsStorage
) : SplashContract.Presenter, BasePresenter<SplashContract.View>() {

    override fun onAppLoaded() {

        if (credentialsStorage.getToken() != null) {
            view?.openMainScreen()
        } else {
            view?.openLoginScreen()
        }
    }
}