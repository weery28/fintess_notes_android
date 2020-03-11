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

class RegistrationActivity :
    BaseActivity<RegistrationContract.View, RegistrationContract.Presenter>(),
    RegistrationContract.View {

    @Inject
    override lateinit var presenter: RegistrationContract.Presenter

    private val registrationButton by lazy { findViewById<TextView>(R.id.tv_registration) }
    private val etLogin by lazy { findViewById<EditText>(R.id.et_login) }
    private val etPassword by lazy { findViewById<EditText>(R.id.et_password) }
    private val etRepeatPassword by lazy { findViewById<EditText>(R.id.et_repeat_password) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setupToolbar()

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
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    private fun onRegistrationClicked() {

        presenter.onRegistrationClicked(
            etLogin.text.toString(),
            etPassword.text.toString(),
            etRepeatPassword.text.toString()
        )
    }
}