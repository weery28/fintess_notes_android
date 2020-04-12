package me.coweery.fitnessnotes.screens.trainings.training

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import me.coweery.fitnessnotes.screens.SimpleDIComponent
import me.coweery.fitnessnotes.screens.trainings.training.input.exercise.InputExerciseContract
import me.coweery.fitnessnotes.screens.trainings.training.input.exercise.InputExerciseFragment
import me.coweery.fitnessnotes.screens.trainings.training.input.exercise.InputExercisePresenter

@Module
class TrainingsModule {

    @Provides
    fun providePresenter(presenter: TrainingPresenter): TrainingContract.Presenter {
        return presenter
    }

    @Provides
    fun provideExerciseInputPresenter(presenter: InputExercisePresenter):
            InputExerciseContract.Presenter {
        return presenter
    }
}

@Subcomponent(modules = [TrainingsModule::class])
interface TrainingsComponent : SimpleDIComponent<TrainingActivity> {

    fun inject(inputExerciseFragment: InputExerciseFragment)
}