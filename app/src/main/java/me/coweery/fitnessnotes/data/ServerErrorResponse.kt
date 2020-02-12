package me.coweery.fleetmanagment.repositories

import com.fasterxml.jackson.annotation.JsonProperty

class ServerErrorResponse(
    @JsonProperty("error")
    val error: String
)