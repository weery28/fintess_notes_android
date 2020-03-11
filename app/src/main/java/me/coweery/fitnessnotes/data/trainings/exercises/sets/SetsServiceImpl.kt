package me.coweery.fitnessnotes.data.trainings.exercises.sets

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SetsServiceImpl @Inject constructor(
    private val setsDAO: SetsDAO
) : SetsService {


    override fun create(set: Set): Single<Set> {

        return setsDAO.insert(set)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .zipWith(Single.just(set)){
                id, s -> s.copy(id = id)
            }

    }
}