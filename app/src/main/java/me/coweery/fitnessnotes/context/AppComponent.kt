package me.coweery.fitnessnotes.context

import dagger.Component
import me.coweery.fitnessnotes.data.AppDataModule
import me.coweery.fitnessnotes.screens.login.LoginComponent
import me.coweery.fleetmanagment.repositories.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, AppDataModule::class))
interface AppComponent {

    fun loginScreenComponent() : LoginComponent
}