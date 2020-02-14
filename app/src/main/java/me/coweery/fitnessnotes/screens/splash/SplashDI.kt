package me.coweery.fitnessnotes.screens.splash

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import me.coweery.fitnessnotes.screens.SimpleDIComponent

@Module
class SplashModule {

    @Provides
    fun presenter(presenter: SplashPresenter): SplashContract.Presenter = presenter
}

@Subcomponent(modules = [SplashModule::class])
interface SplashComponent : SimpleDIComponent<SplashActivity>