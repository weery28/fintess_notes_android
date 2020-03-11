package me.coweery.fitnessnotes.data.exercises

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface ExercisesDAO {

    @Insert
    fun insert(exercise: Exercise): Single<Long>

    @Query("SELECT * FROM exercise WHERE trainingId = :id")
    fun getByTrainingId(id : Long) : Single<List<Exercise>>
}