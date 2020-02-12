package me.coweery.fitnessnotes.screens

import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : MvpContract.View> : MvpContract.Presenter<T> {

    protected var view : T? = null
    private val disposables = mutableListOf<Disposable>()

    override fun atachView(view: T) {
        this.view = view
    }

    override fun disposeAll() {
        disposables.forEach {
            if (!it.isDisposed){
                it.dispose()
            }
        }
    }

    protected fun onSubscribe(disposable: Disposable){
        disposables.add(disposable)
    }

    protected fun onComplete(disposable: Disposable){
        disposables.remove(disposable)
    }
}