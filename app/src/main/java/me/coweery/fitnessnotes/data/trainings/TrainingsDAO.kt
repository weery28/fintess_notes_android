package me.coweery.fitnessnotes.data.trainings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TrainingsDAO {

    @Insert
    fun insert(trainings : Training) : Completable

    @Query("SELECT * FROM training")
    fun getAll() : Single<List<Training>>

    @Query("SELECT count(id) FROM training")
    fun count() : Single<Long>
}