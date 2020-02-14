package me.coweery.fitnessnotes.data.login

import io.reactivex.Observable

interface UnauthorizedErrorService {

    fun unauthorizedErrorObservable(): Observable<UnauthorizedError>
}