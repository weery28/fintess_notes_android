package me.coweery.fitnessnotes.data.login

import io.reactivex.Completable

interface LoginService {

    fun login(login: String, password: String): Completable
}