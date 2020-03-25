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
    fun getFullTraining(id: Long): Maybe<FullTraining>
}