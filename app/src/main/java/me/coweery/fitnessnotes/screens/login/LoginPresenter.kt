package me.coweery.fitnessnotes.screens.login

import me.coweery.fitnessnotes.screens.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor() :
    BasePresenter<LoginContract.View>(),
    LoginContract.Presenter