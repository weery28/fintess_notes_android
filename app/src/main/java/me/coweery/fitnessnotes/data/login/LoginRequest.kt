package me.coweery.fitnessnotes.data.login

import com.fasterxml.jackson.annotation.JsonProperty

class LoginRequest(

    @field:JsonProperty("login")
    val login: String,

    @field:JsonProperty("password")
    val password: String
)