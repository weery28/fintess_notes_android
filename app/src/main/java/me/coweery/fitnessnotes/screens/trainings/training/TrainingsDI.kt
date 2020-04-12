package me.coweery.fitnessnotes.screens.trainings.training

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import me.coweery.fitnessnotes.screens.SimpleDIComponent

@Module
class TrainingsModule {

    @Provides
    fun providePresenter(presenter: TrainingPresenter): TrainingContract.Presenter {
        return presenter
    }
}

@Subcomponent(modules = [TrainingsModule::class])
interface TrainingsComponent : SimpleDIComponent<TrainingActivity>