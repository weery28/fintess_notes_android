package me.coweery.fitnessnotes.data.login.models

import com.fasterxml.jackson.annotation.JsonProperty

class GoogleLoginRequest(

    @field:JsonProperty("googleToken")
    val googleToken: String
)