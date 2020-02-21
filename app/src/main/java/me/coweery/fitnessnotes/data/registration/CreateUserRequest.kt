package me.coweery.fitnessnotes.data.registration

import com.fasterxml.jackson.annotation.JsonProperty

class CreateUserRequest(
    @JsonProperty("login")
    val login: String? = null,

    @JsonProperty("password")
    val password: String? = null
)