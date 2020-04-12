package me.coweery.fitnessnotes.screens.trainings.list

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import me.coweery.fitnessnotes.screens.SimpleDIComponent

@Module
class TrainingsListModule {

    @Provides
    fun providePresenter(presenter: TrainingsListPresenter): TrainingsListContract.Presenter {
        return presenter
    }
}

@Subcomponent(modules = [TrainingsListModule::class])
interface TrainingsListComponent : SimpleDIComponent<TrainingsListActivity>