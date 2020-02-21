package me.coweery.fitnessnotes.screens.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textview.MaterialTextView
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.screens.BaseActivity
import me.coweery.fitnessnotes.screens.registration.RegistrationActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(),
    LoginContract.View {

    companion object {

        private val RC_SIGN_IN = "RC_SIGN_IN".hashCode() and 0x0000ffff
    }

    @Inject
    override lateinit var presenter: LoginContract.Presenter

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val signInByGoogleButton by lazy { findViewById<View>(R.id.ll_sign_in_google) }
    private val signInBasicButton by lazy { findViewById<MaterialTextView>(R.id.btn_sign_in) }
    private val etLogin by lazy { findViewById<EditText>(R.id.et_login) }
    private val etPassword by lazy { findViewById<EditText>(R.id.et_password) }
    private val registrationButton by lazy { findViewById<TextView>(R.id.tv_registration) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mGoogleSignInClient = GoogleSignIn
            .getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(getString(R.string.oauth_client_id))
                    .build()
            )

        signInByGoogleButton.setOnClickListener {
            onSignInWithGoogleClicked()
        }

        signInBasicButton.setOnClickListener {
            onSignInBasicClicked()
        }

        registrationButton.setOnClickListener {
            onRegistrationClicked()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                task.getResult(ApiException::class.java)
                    ?.idToken?.let(presenter::onGoogleTokenRecieved)
            } catch (e: ApiException) {
                // TODO
            }
        }
    }

    override fun setupDI() {
        AppContext.appComponent.loginScreenComponent().inject(this)
    }

    override fun openMainScreen() {
        Toast
            .makeText(this, "Authorization OK!", Toast.LENGTH_LONG)
            .show()
    }

    override fun onAuthorizationFailed(message: String) {
        Toast
            .makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }

    private fun onSignInWithGoogleClicked() {
        startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
    }

    private fun onSignInBasicClicked() {
        presenter.onBasicLoginDataRecieved(
            etLogin.text.toString(), etPassword.text.toString()
        )
    }

    private fun onRegistrationClicked(){
        startActivity(Intent(this, RegistrationActivity::class.java))
    }
}