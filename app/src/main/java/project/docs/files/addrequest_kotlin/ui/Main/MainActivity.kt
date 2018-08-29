package project.docs.files.addrequest_kotlin.ui.Main

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import project.docs.files.addrequest_kotlin.R
import project.docs.files.addrequest_kotlin.settings.UserProfileSettings
import project.docs.files.addrequest_kotlin.threads.FirebaseDbListenerService
import project.docs.files.addrequest_kotlin.ui.TicketList.TicketListActivity

class MainActivity : AppCompatActivity() {


    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        FirebaseApp.getApps(this)
        mFirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuthWithGoogle()
    }


    private fun setupActionBar() {

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }

    }


    private fun firebaseAuthWithGoogle() {
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                gotoTicketList()
            } else {
                goToLogin()
            }
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                gotoTicketList()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        mFirebaseAuth?.addAuthStateListener(mAuthStateListener!!)
    }


    override fun onPause() {
        super.onPause()
        if (mAuthStateListener != null) {
            mFirebaseAuth?.removeAuthStateListener(mAuthStateListener!!)
        }
    }


    private fun gotoTicketList() {

        val userId = mFirebaseAuth!!.currentUser?.uid
        val userName = mFirebaseAuth!!.currentUser?.displayName
        val userPhotoUrl = mFirebaseAuth!!.currentUser?.photoUrl.toString()

        UserProfileSettings.setUserProfileAtLogin(
                userId!!,
                userName,
                userPhotoUrl)

        startService(Intent(this, FirebaseDbListenerService::class.java))

        if (mAuthStateListener != null) {
            mFirebaseAuth?.removeAuthStateListener(mAuthStateListener!!)
        }

        val intent = Intent(this, TicketListActivity::class.java)
        startActivity(intent)
    }


    private fun goToLogin() {

        val handler = Handler()
        handler.postDelayed({
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(
                                    AuthUI.GOOGLE_PROVIDER)
                            .build(),
                    RC_SIGN_IN)
        }, 3000)   //3 second

    }


    override fun onBackPressed() {
        // do nothing
    }


}
