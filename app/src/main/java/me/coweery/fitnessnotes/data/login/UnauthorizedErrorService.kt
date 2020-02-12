package me.coweery.fitnessnotes.data.login

import io.reactivex.Observable
import me.coweery.fitnessnotes.data.login.UnauthorizedError

interface UnauthorizedErrorService {

    fun unauthorizedErrorObservable(): Observable<UnauthorizedError>
}