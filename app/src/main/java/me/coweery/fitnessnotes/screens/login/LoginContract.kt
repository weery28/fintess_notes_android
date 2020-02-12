package me.coweery.fitnessnotes.screens.login

import me.coweery.fitnessnotes.screens.MvpContract

interface LoginContract {

    interface View : MvpContract.View

    interface Presenter : MvpContract.Presenter<View>
}