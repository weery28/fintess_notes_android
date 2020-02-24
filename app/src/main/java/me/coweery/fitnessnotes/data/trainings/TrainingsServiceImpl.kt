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

        return trainingsDAO.count()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
            if (it == 0L){
                Observable.fromIterable(0..10)
                    .flatMapCompletable {
                        trainingsDAO.insert(
                            Training(it.toLong(), "Тренировка $it", Random.nextBoolean())
                        )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())

                    }
                    .andThen(trainingsDAO.getAll().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))


            } else {
                trainingsDAO.getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }

    override fun save(training: Training): Completable {
        return trainingsDAO
            .insert(training)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}