package me.coweery.fitnessnotes.data

import androidx.room.RoomDatabase
import androidx.room.Database
import me.coweery.fitnessnotes.data.trainings.Training
import me.coweery.fitnessnotes.data.trainings.TrainingsDAO


@Database(
    entities = [Training::class /*, AnotherEntityType.class, AThirdEntityType.class */],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val trainingsDAO: TrainingsDAO
}