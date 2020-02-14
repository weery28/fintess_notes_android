package me.coweery.fitnessnotes.screens.splash

import me.coweery.fitnessnotes.screens.MvpContract

interface SplashContract {

    interface View : MvpContract.View {

        fun openMainScreen()

        fun openLoginScreen()
    }

    interface Presenter : MvpContract.Presenter<View> {

        fun onAppLoaded()
    }
}