package me.coweery.fitnessnotes.data.trainings.exercises

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ExercisesDAO {

    @Insert
    fun insert(exercise: Exercise): Single<Long>

    @Update
    fun update(exercise: Exercise): Completable

    @Query("SELECT * FROM exercise WHERE trainingId = :id")
    fun getByTrainingId(id: Long): Single<List<Exercise>>

    @Query("DELETE FROM exercise WHERE id = :id")
    fun delete(id: Long): Completable

    @Query("SELECT * FROM exercise WHERE trainingId = :id AND name = :name LIMIT 1")
    fun getByTrainingIdAndName(id: Long, name: String): Maybe<Exercise>
}