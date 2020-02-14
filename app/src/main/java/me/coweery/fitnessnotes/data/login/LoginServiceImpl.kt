package me.coweery.fitnessnotes.data.login

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.coweery.fitnessnotes.data.login.models.GoogleLoginRequest
import me.coweery.fitnessnotes.data.login.models.LoginRequest
import javax.inject.Inject

class LoginServiceImpl @Inject constructor(
    private val loginResource: LoginResource
) : LoginService {

    override fun login(login: String, password: String): Completable {
        return loginResource.login(
            LoginRequest(
                login,
                password
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun loginWithGoogle(googleToken: String): Completable {

        return loginResource.login(GoogleLoginRequest((googleToken)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}