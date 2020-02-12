package me.coweery.fitnessnotes.screens

interface MvpContract {

    interface View

    interface Presenter<T : View> {

        fun atachView(view: T)

        fun disposeAll()
    }
}