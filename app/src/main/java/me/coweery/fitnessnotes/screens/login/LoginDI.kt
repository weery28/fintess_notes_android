package me.coweery.fitnessnotes.screens.login

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import me.coweery.fitnessnotes.screens.SimpleDIComponent

@Module
class LoginModule {

    @Provides
    fun loginPresenter(loginPresenter: LoginPresenter): LoginContract.Presenter {
        return loginPresenter
    }
}

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent : SimpleDIComponent<LoginActivity>