package me.coweery.fitnessnotes.screens.registration

import me.coweery.fitnessnotes.data.login.LoginService
import me.coweery.fitnessnotes.data.registration.RegistrationService
import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class RegistrationPresenter @Inject constructor(
    private val registrationService: RegistrationService,
    private val loginService: LoginService
) :
    RegistrationContract.Presenter, BasePresenter<RegistrationContract.View>() {

    override fun onRegistrationClicked(login: String, password: String, repeatPassword: String) {

        if (password == repeatPassword){
            registrationService.register(login, password)
                .andThen(loginService.login(login, password))
                .safetySubscribe(
                    {
                        view?.openMainScreen()
                    },
                    {
                        it.printStackTrace()
                    }
                )
        } else {
            view?.showDifferentPasswords()
        }
    }
}