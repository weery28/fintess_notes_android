package me.coweery.fitnessnotes.screens

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import me.coweery.fitnessnotes.R

abstract class BaseActivity<S : MvpContract.View, T : MvpContract.Presenter<S>> : MvpContract.View,
    AppCompatActivity() {

    protected abstract val presenter: T
    protected val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

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

    protected fun setupToolbar(){

        setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent))
        toolbar.apply {
            setBackgroundColor(Color.TRANSPARENT)
            setSupportActionBar(this)
            supportActionBar?.title = null
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setBackArrowColor(ContextCompat.getColor(this, android.R.color.white))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.statusBarColor = color
        }
    }

    private fun setBackArrowColor(color: Int) {
        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

}