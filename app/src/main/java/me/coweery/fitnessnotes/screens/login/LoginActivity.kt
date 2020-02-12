package me.coweery.fitnessnotes.screens.login

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import me.coweery.fitnessnotes.screens.BaseActivity
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    companion object {

        private val RC_SIGN_IN = "RC_SIGN_IN".hashCode() and 0x0000ffff
    }

    @Inject
    override lateinit var presenter: LoginContract.Presenter

    private lateinit var mGoogleSignInClient : GoogleSignInClient
    private val signInButton by lazy { findViewById<SignInButton>(R.id.sign_in_button) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppContext.appComponent.loginScreenComponent().inject(this)
        presenter.atachView(this)

        mGoogleSignInClient = GoogleSignIn
            .getClient(
                this,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .requestIdToken(getString(R.string.oauth_client_id))
                    .build()
            )

        signInButton.setSize(SignInButton.SIZE_ICON_ONLY)
        signInButton.setOnClickListener {
            onSignInWithGoogleCliced()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                task.getResult(ApiException::class.java)?.idToken?.let(presenter::onGoogleTokenRecieved)
            } catch (e: ApiException) {
                // TODO
            }
        }
    }

    private fun onSignInWithGoogleCliced(){
        startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
    }
}