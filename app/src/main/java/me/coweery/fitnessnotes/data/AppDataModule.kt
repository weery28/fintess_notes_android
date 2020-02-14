package me.coweery.fitnessnotes.data

import dagger.Module
import dagger.Provides
import me.coweery.fitnessnotes.data.login.BinaryPrefsCredentialsStorageImpl
import me.coweery.fitnessnotes.data.login.CredentialsStorage
import me.coweery.fitnessnotes.data.login.LoginResource
import me.coweery.fitnessnotes.data.login.LoginService
import me.coweery.fitnessnotes.data.login.LoginServiceImpl
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppDataModule {

    //region ==================== Auth ====================

    @Provides
    @Singleton
    fun provideCredentialsStorage(credentialsStorage: BinaryPrefsCredentialsStorageImpl): CredentialsStorage {
        return credentialsStorage
    }

    @Provides
    @Singleton
    fun provideLoginResource(@Named("api") retrofit: Retrofit): LoginResource {
        return retrofit.create(LoginResource::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginService(authService: LoginServiceImpl): LoginService {
        return authService
    }

    //endregion
}