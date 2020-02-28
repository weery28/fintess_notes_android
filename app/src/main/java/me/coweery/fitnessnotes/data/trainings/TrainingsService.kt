package me.coweery.fitnessnotes.data.trainings

import io.reactivex.Completable
import io.reactivex.Single

interface TrainingsService {

    fun getAll(): Single<List<Training>>

    fun save(training: Training): Completable
}