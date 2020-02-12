package me.coweery.fitnessnotes.data.login

interface CredentialsStorage {

    fun saveToken(token: String?)

    fun getToken(): String?
}