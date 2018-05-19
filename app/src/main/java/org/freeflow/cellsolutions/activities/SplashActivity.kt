package org.freeflow.cellsolutions.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.freeflow.cellsolutions.R
import org.freeflow.cellsolutions.models.User
import org.jetbrains.anko.startActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 23
    private val SPLASH_DISPLAY_LENGTH: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(({
            signInHandler()
        }), SPLASH_DISPLAY_LENGTH)
    }

    private fun signInHandler() {
        val mFirebaseAuth = FirebaseAuth.getInstance()
        val user = mFirebaseAuth.currentUser

        if (user != null) {
            startActivity<MainActivity>()
            finish()
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(
                                    Arrays.asList<AuthUI.IdpConfig>(
                                            AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val fbUser = FirebaseAuth.getInstance().currentUser!!
                val userRef = FirebaseDatabase.getInstance().getReference("/users/${fbUser.uid}")

                val user = User(name = fbUser.displayName.toString(),
                        identifier = if (fbUser.email.isNullOrEmpty()) fbUser.phoneNumber.toString() else fbUser.email.toString()
                )
                userRef.setValue(user)

                signInHandler()
            } else finish()
        } else {
            finish()
        }
    }
}
