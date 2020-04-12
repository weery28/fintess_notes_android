package me.coweery.fitnessnotes.data.trainings

import io.reactivex.Single

interface TrainingsService {

    fun getAll(): Single<List<Training>>

    fun save(training: Training): Single<Long>

    fun get(id: Long): Single<Training>

    fun getFullTraining(id: Long): Single<FullTraining>
}