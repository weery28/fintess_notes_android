package me.coweery.fitnessnotes.data.login

import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observables.ConnectableObservable
import javax.inject.Inject

class AuthErrorListenerService @Inject constructor(
    private val credentialsStorage: CredentialsStorage
) : UnauthorizedErrorListener,
    UnauthorizedErrorService {

    private val authErrorObservable: ConnectableObservable<UnauthorizedError>
    private var emitter: Emitter<UnauthorizedError>? = null

    init {
        authErrorObservable = Observable.create<UnauthorizedError> { emitter ->
            this@AuthErrorListenerService.emitter = emitter
        }.observeOn(AndroidSchedulers.mainThread()).publish()
        authErrorObservable.connect()
    }

    //region ==================== UnauthorizedErrorListener ====================

    override fun onReceivedUnauthorizedError() {
        credentialsStorage.saveToken(null)
        emitter?.onNext(UnauthorizedError())
    }

    //endregion

    //region ==================== UnauthorizedErrorService ====================

    override fun unauthorizedErrorObservable(): Observable<UnauthorizedError> {
        return authErrorObservable
    }

    //endregion

}

class UnauthorizedError() {

}