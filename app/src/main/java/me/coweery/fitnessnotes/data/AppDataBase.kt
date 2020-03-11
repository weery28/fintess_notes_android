package me.coweery.fitnessnotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import me.coweery.fitnessnotes.data.exercises.Exercise
import me.coweery.fitnessnotes.data.exercises.ExercisesDAO
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsDAO


@Database(
    entities = [Training::class, Exercise::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract val trainingsDAO: TrainingsDAO
    abstract val exercisesDAO: ExercisesDAO
}