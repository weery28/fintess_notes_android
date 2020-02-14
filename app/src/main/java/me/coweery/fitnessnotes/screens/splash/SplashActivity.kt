package me.coweery.fitnessnotes.screens.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.login.LoginActivity
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashContract.View, SplashContract.Presenter>(),
    SplashContract.View {

    @Inject
    override lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun setupDI() {
        AppContext.appComponent.splashScreenComponent().inject(this)
    }

    override fun openMainScreen() {

        Toast
            .makeText(this, "Already authorized", Toast.LENGTH_LONG)
            .show()
    }

    override fun openLoginScreen() {
        startActivity(
            Intent(this, LoginActivity::class.java)
        )
    }
}