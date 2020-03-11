package me.coweery.fitnessnotes.data.trainings

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class TrainingsServiceImpl @Inject constructor(
    private val trainingsDAO: TrainingsDAO
) : TrainingsService {

    override fun getAll(): Single<List<Training>> {

        return trainingsDAO.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun save(training: Training): Single<Long> {
        return trainingsDAO
            .insert(training)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun get(id: Long): Single<Training> {

        return trainingsDAO.get(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toSingle()
    }
}