package me.coweery.fitnessnotes.data.trainings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface TrainingsDAO {

    @Insert
    fun insert(trainings: Training): Single<Long>

    @Query("SELECT * FROM training")
    fun getAll(): Single<List<Training>>

    @Query("SELECT * FROM training WHERE id =:id")
    fun get(id: Long): Maybe<Training>

    @Query("SELECT * FROM training WHERE id =:id")
    fun getFullTraining(id: Long): Maybe<TrainingWithExercises>

    @Query(
        """
        SELECT training.* FROM training
        JOIN exercise ON training.id = exercise.trainingId
        WHERE exercise.name = :name
        ORDER BY date DESC
        LIMIT 1
    """
    )
    fun getLastWithExercise(name: String): Maybe<Training>

    @Query(
        """
        SELECT training.* FROM training
        JOIN exercise ON training.id = exercise.trainingId
        WHERE exercise.name = :name AND training.id != :exceptId
        ORDER BY date DESC
        LIMIT 1
    """
    )
    fun getLastWithExercise(name: String, exceptId: Long): Maybe<Training>
}