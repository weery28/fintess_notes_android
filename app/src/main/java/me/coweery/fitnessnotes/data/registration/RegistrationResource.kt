package me.coweery.fitnessnotes.data.registration

import io.reactivex.Completable
import retrofit2.http.Body

interface RegistrationResource {

    fun registration(@Body request: CreateUserRequest): Completable
}