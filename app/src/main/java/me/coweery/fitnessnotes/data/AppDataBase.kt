package me.coweery.fitnessnotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise
import me.coweery.fitnessnotes.data.trainings.exercises.ExercisesDAO
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsDAO
import me.coweery.fitnessnotes.data.trainings.exercises.sets.Set
import me.coweery.fitnessnotes.data.trainings.exercises.sets.SetsDAO


@Database(
    entities = [Training::class, Exercise::class, Set::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract val trainingsDAO: TrainingsDAO
    abstract val exercisesDAO: ExercisesDAO
    abstract val setsDAO : SetsDAO
}