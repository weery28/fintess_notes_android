package me.coweery.fitnessnotes.screens.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import me.coweery.fitnessnotes.R
import me.coweery.fitnessnotes.context.AppContext
import javax.inject.Inject
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException




class LoginActivity : AppCompatActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter


    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestServerAuthCode("227809934036-7u3qs9rcct6jhmpsnlc82jephe8n11fl.apps.googleusercontent.com")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppContext.appComponent.loginScreenComponent().inject(this)
        presenter.atachView(this)

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null){
            println(account.email)
        }

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener{
            startActivityForResult(mGoogleSignInClient.signInIntent,1 )

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            try {

                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                println(task.getResult(ApiException::class.java)?.idToken)
            } catch (e : ApiException){
                println(e.message)
            }

        }
    }


}