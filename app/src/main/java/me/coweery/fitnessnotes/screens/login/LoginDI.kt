package me.coweery.fitnessnotes.screens.login

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class LoginModule {

    @Provides
    fun loginPresenter(loginPresenter : LoginPresenter) : LoginContract.Presenter {
        return loginPresenter
    }
}

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent{

    fun inject(loginActivity: LoginActivity)
}