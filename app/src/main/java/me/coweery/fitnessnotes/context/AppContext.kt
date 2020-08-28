package me.coweery.fitnessnotes.context

import android.app.Application
import me.coweery.fitnessnotes.Database

class AppContext : Application() {

    companion object {
        lateinit var instance: AppContext
            private set

        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppContext.appComponent = initDaggerComponent()
    }

    private fun initDaggerComponent(): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}