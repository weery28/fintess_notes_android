package me.coweery.fitnessnotes.screens

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpContract.View> : MvpContract.Presenter<T> {

    protected var view: T? = null
    private val disposables = mutableListOf<Disposable>()

    override fun atachView(view: T) {
        this.view = view
    }

    override fun disposeAll() {
        disposables.forEach {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }

    protected fun Disposable.catchDisposable() {
        disposables.add(this)
        disposables.removeAll { it.isDisposed }
    }

    protected fun <R> Single<R>.safetySubscribe(
        onComplete: (R) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.add(subscribe(onComplete, onError))
        disposables.removeAll { it.isDisposed }
    }

    protected fun <R> Maybe<R>.safetySubscribe(
        onSucces: (R) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete : () -> Unit
    ) {
        disposables.add(subscribe(onSucces, onError, onComplete))
        disposables.removeAll { it.isDisposed }
    }

    protected fun Completable.safetySubscribe(
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        disposables.add(subscribe(onComplete, onError))
        disposables.removeAll { it.isDisposed }
    }
}