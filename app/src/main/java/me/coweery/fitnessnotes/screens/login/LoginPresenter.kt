package me.coweery.fitnessnotes.screens.login

import io.reactivex.Completable
import me.coweery.fitnessnotes.data.login.LoginService
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginService: LoginService
) :
    BasePresenter<LoginContract.View>(),
    LoginContract.Presenter {

    override fun onGoogleTokenRecieved(googleToken: String) {

        loginService.loginWithGoogle(googleToken)
            .doAfterLogin()
            .catchDisposable()
    }

    override fun onBasicLoginDataRecieved(login: String, password: String) {

        loginService.login(login, password)
            .doAfterLogin()
            .catchDisposable()
    }

    private fun Completable.doAfterLogin() =
        subscribe(
            {
                view?.openMainScreen()
            },
            {
                view?.onAuthorizationFailed(it.message ?: it.localizedMessage ?: "failed")
            }
        )
}