package me.coweery.fitnessnotes.screens.registration

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import me.coweery.fitnessnotes.screens.SimpleDIComponent

@Module
class RegistrationModule {

    @Provides
    fun providePresnter(presenter: RegistrationPresenter): RegistrationContract.Presenter {
        return presenter
    }
}

@Subcomponent(modules = [RegistrationModule::class])
interface RegistrationComponent : SimpleDIComponent<RegistrationActivity>