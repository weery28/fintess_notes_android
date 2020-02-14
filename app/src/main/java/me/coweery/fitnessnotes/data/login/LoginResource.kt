package me.coweery.fitnessnotes.data.login

import io.reactivex.Completable
import me.coweery.fitnessnotes.data.login.models.GoogleLoginRequest
import me.coweery.fitnessnotes.data.login.models.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginResource {

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Completable

    @POST("/login-google")
    fun login(@Body loginRequest: GoogleLoginRequest): Completable
}