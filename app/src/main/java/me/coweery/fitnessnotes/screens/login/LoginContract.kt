package me.coweery.fitnessnotes.screens.login

import me.coweery.fitnessnotes.screens.MvpContract

interface LoginContract {

    interface View : MvpContract.View {

        fun openMainScreen()

        fun onAuthorizationFailed(message: String)
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onGoogleTokenRecieved(googleToken: String)

        fun onBasicLoginDataRecieved(login: String, password: String)
    }
}