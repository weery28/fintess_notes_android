package me.coweery.fitnessnotes.data.trainings.exercises.sets

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Single

@Dao
interface SetsDAO {

    @Insert
    fun insert(set : Set): Single<Long>

    @Update
    fun update(set : Set): Single<Int>

    @Query("SELECT * FROM `set` WHERE exerciseId = :exerciseId")
    fun getByExerciseId(exerciseId : Long) : Single<List<Set>>

}