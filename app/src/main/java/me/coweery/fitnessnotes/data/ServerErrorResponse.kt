package me.coweery.fitnessnotes.data

import com.fasterxml.jackson.annotation.JsonProperty

class ServerErrorResponse(
    @JsonProperty("error")
    val error: String
)