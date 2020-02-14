package me.coweery.fitnessnotes.data.login

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import javax.inject.Inject

class BinaryPrefsCredentialsStorageImpl @Inject constructor(context: Context) :
    CredentialsStorage {

    companion object {

        @JvmField
        val PREFERENCES_FILENAME = "credentials"
        @JvmField
        val KEY_TOKEN = "token"
    }

    private val preferences = BinaryPreferencesBuilder(context)
        .name(PREFERENCES_FILENAME)
        .build()

    //region ==================== CredentialsStorage ====================

    override fun saveToken(token: String?) {
        preferences.edit().putString(KEY_TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
    }

    //endregion
}