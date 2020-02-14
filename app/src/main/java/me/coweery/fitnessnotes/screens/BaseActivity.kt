package me.coweery.fitnessnotes.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
}