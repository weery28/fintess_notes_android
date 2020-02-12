package me.coweery.fitnessnotes.data.login

import io.reactivex.Completable
import me.coweery.fitnessnotes.data.login.LoginRequest
import me.coweery.fitnessnotes.data.login.LoginResource
import me.coweery.fitnessnotes.data.login.LoginService
import javax.inject.Inject

class LoginServiceImpl @Inject constructor(
    private val loginResource: LoginResource
) : LoginService {

    override fun login(login: String, password: String): Completable {
        return loginResource.login(LoginRequest(login, password))
    }
}