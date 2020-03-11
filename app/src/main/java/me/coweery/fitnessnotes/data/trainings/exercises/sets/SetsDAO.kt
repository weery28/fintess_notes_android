package me.coweery.fitnessnotes.data.trainings.exercises.sets

import androidx.room.Dao
import androidx.room.Insert
import io.reactivex.Single
import me.coweery.fitnessnotes.data.trainings.exercises.Exercise

@Dao
interface SetsDAO {

    @Insert
    fun insert(set : Set): Single<Long>

}