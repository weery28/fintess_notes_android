package me.coweery.fitnessnotes.screens.registration

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.screens.BaseActivity
import javax.inject.Inject

class RegistrationActivity : BaseActivity<RegistrationContract.View, RegistrationContract.Presenter>(), RegistrationContract.View {

    @Inject
    override lateinit var presenter: RegistrationContract.Presenter

    private val registrationButton by lazy { findViewById<TextView>(R.id.tv_registration) }
    private val etLogin by lazy { findViewById<EditText>(R.id.et_login) }
    private val etPassword by lazy { findViewById<EditText>(R.id.et_password) }
    private val etRepeatPassword by lazy { findViewById<EditText>(R.id.et_repeat_password) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent))

        findViewById<Toolbar>(R.id.toolbar).apply {
            setBackgroundColor(Color.TRANSPARENT)
            setSupportActionBar(this)
            supportActionBar?.title = null
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        setBackArrowColor(ContextCompat.getColor(this, android.R.color.white))

        registrationButton.setOnClickListener {
            onRegistrationClicked()
        }

        etRepeatPassword.setOnFocusChangeListener { v, hasFocus ->
            etRepeatPassword.error = null
        }

        etPassword.setOnFocusChangeListener { v, hasFocus ->
            etPassword.error = null
        }
    }

    override fun setupDI() {
        AppContext.appComponent.registrationScreenComponent().inject(this)
    }

    override fun showDifferentPasswords() {
        etRepeatPassword.error = "Different passwords"
        etPassword.error = "Different passwords"
    }

    override fun openMainScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onRegistrationClicked(){

        presenter.onRegistrationClicked(
            etLogin.text.toString(),
            etPassword.text.toString(),
            etRepeatPassword.text.toString()
        )
    }

    fun AppCompatActivity.setStatusBarColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.statusBarColor = color
        }
    }

    fun AppCompatActivity.setBackArrowColor(color: Int) {
        val upArrow: Drawable? = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material)
        upArrow?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }
}