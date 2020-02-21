package me.coweery.fitnessnotes.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem


abstract class BaseActivity<S : MvpContract.View, T : MvpContract.Presenter<S>> : MvpContract.View,
    AppCompatActivity() {

    protected abstract val presenter: T

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDI()
        presenter.atachView(this as S)
    }

    abstract fun setupDI()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}