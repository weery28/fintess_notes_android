package me.coweery.fitnessnotes.screens.registration

import me.coweery.fitnessnotes.screens.MvpContract

interface RegistrationContract {

    interface View : MvpContract.View {

        fun showDifferentPasswords()

        fun openMainScreen()
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onRegistrationClicked(login : String, password : String, repeatPassword : String)
    }
}