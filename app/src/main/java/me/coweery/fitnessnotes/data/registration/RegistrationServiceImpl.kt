package me.coweery.fitnessnotes.data.registration

import io.reactivex.Completable
import javax.inject.Inject

class RegistrationServiceImpl @Inject constructor(
    private val registrationResource: RegistrationResource
) : RegistrationService {

    override fun register(login: String, password: String): Completable {
        return registrationResource.registration(CreateUserRequest(login, password))
    }
}