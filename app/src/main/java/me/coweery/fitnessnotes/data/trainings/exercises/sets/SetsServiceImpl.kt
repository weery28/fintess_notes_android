package me.coweery.fitnessnotes.data.trainings.exercises.sets

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SetsServiceImpl @Inject constructor(
    private val setsDAO: SetsDAO
) : SetsService {


    override fun createOrUpdate(set: Set): Single<Set> {

        return setsDAO.update(set)
            .flatMap {
                if (it == 0){
                    setsDAO.insert(set)
                        .zipWith(Single.just(set)){
                                id, s -> s.copy(id = id)
                        }
                } else {
                    Single.just(set)
                }
            }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getByExerciseId(exerciseId: Long): Single<List<Set>> {

        return setsDAO.getByExerciseId(exerciseId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}