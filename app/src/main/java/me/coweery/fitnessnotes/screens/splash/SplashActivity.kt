package me.coweery.fitnessnotes.screens.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.reactivex.Single
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.trainings.list.TrainingsListActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashActivity : BaseActivity<SplashContract.View, SplashContract.Presenter>(),
    SplashContract.View {

    @Inject
    override lateinit var presenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent))
        Single.timer(1, TimeUnit.SECONDS).subscribe { _, _ ->
            presenter.onAppLoaded()
        }
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
            Intent(this, TrainingsListActivity::class.java)
        )
        finish()
    }
}