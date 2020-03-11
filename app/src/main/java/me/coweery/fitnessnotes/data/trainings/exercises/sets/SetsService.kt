package me.coweery.fitnessnotes.data.trainings.exercises.sets

import io.reactivex.Single

interface SetsService {

    fun create(set: Set): Single<Set>
}