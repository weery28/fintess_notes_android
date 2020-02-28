package me.coweery.fitnessnotes.data.exercises

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Single

@Dao
interface ExercisesDAO {

    @Insert
    fun insert(exercise: Exercise): Single<Long>
}