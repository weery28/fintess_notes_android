package me.coweery.fitnessnotes.data.registration

import io.reactivex.Completable

interface RegistrationService {

    fun register(login: String, password: String): Completable
}