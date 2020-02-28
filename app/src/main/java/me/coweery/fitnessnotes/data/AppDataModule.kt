package me.coweery.fitnessnotes.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.coweery.fitnessnotes.data.exercises.ExercisesDAO
import me.coweery.fitnessnotes.data.login.*
import me.coweery.fitnessnotes.data.registration.RegistrationResource
import me.coweery.fitnessnotes.data.registration.RegistrationService
import me.coweery.fitnessnotes.data.registration.RegistrationServiceImpl
import me.coweery.fitnessnotes.data.trainings.TrainingsDAO
import me.coweery.fitnessnotes.data.trainings.TrainingsService
import me.coweery.fitnessnotes.data.trainings.TrainingsServiceImpl
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

    //region ==================== Registration ====================

    @Provides
    @Singleton
    fun provideRegistrationResource(@Named("api") retrofit: Retrofit): RegistrationResource {
        return retrofit.create(RegistrationResource::class.java)
    }

    @Provides
    @Singleton
    fun provideRegistrationService(registrationServiceImpl: RegistrationServiceImpl): RegistrationService {
        return registrationServiceImpl
    }

    //endregion

    //region ==================== Trainings ====================


    @Provides
    @Singleton
    fun provideTrainingsService(trainingsServiceImpl: TrainingsServiceImpl): TrainingsService {
        return trainingsServiceImpl
    }

    //endregion

    //region ==================== Room ====================

    @Provides
    @Singleton
    fun provideAppDatabase(appContext: Context): AppDataBase {

        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java, "fn_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrainingsDao(appDatabase: AppDataBase): TrainingsDAO {

        return appDatabase.trainingsDAO
    }

    @Provides
    @Singleton
    fun provideExercisesDao(appDatabase: AppDataBase): ExercisesDAO {

        return appDatabase.exercisesDAO
    }

    //endregion
}