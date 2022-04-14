package com.example.joballey



import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.joballey.UserProfilePage.UserFragment
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


open class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mUser: FirebaseUser
    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login: TextView = findViewById(R.id.sign_in)
        val registration: TextView = findViewById(R.id.register)
        val fbLogin: Button = findViewById(R.id.login_button)
        val googleLogin: Button = findViewById(R.id.google_sign_in)
        val forgotPassword: TextView = findViewById(R.id.forgot_password)
        val email: EditText = findViewById(R.id.edit_email)
        val password: EditText = findViewById(R.id.edit_password)


        //auth = Firebase.auth
        auth = FirebaseAuth.getInstance()


        //DoGoogleSignIn()

        login.setOnClickListener {
            val useremail = email.text.toString()
            val userpassword = password.text.toString()

            //val user = auth.currentUser
            if(TextUtils.isEmpty(useremail) && TextUtils.isEmpty(userpassword)) {
                Toast.makeText(this, "Login failure", Toast.LENGTH_SHORT).show()
            }
            else {
                signIn(useremail, userpassword)

            }


        }

        registration.setOnClickListener {

            val intent1 = Intent(this, RegisterActivity::class.java)
            startActivity(intent1)
        }

        forgotPassword.setOnClickListener {

            val intent3 = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent3)
        }

        googleLogin.setOnClickListener {
            //signIn()
        }

        fbLogin.setOnClickListener {
            //facebookLogin()
        }

    }


    private fun signIn(email: String, password:String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    checkUser()
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "signInWithEmail:success", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "signInWithEmail:success")
                    val intent7 = Intent(this, MainActivity2::class.java)
                    startActivity(intent7)
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun checkUser(){
        val firebaseUser = auth.currentUser!!
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

//    private fun DoGoogleSignIn() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e)
//            }
//        }
//        else {
//            callbackManager.onActivityResult(requestCode, resultCode, data)
//        }
//
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                        Toast.makeText(this,"signInWithGoogle:success", Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, "signInWithCredential:success")
//                    val intent7 = Intent(this, HomepageActivity::class.java)
//                    startActivity(intent7)
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(this,"signInWithGoogle:failure", Toast.LENGTH_SHORT).show()
//                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
//                }
//            }
//    }
//
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    private fun facebookLogin() {
//        callbackManager = CallbackManager.Factory.create()
//
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"))
//        LoginManager.getInstance().registerCallback(callbackManager,
//            object : FacebookCallback<LoginResult> {
//                override fun onSuccess(result: LoginResult) {
//                    Log.d(TAG, "facebook:onSuccess:$result")
//                    handleFacebookAccessToken(result.accessToken)
//                }
//
//                override fun onCancel() {
//                    Log.d(TAG, "facebook:onCancel")
//                }
//
//                override fun onError(error: FacebookException) {
//                    Log.d(TAG, "facebook:onError", error)
//                }
//            })
//    }
//
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
//    private fun handleFacebookAccessToken(token: AccessToken) {
//        Log.d(TAG, "handleFacebookAccessToken:$token")
//
//        val credential = FacebookAuthProvider.getCredential(token.token)
//        auth.signInWithCredential(credential)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Toast.makeText(this,"signInWithFacebook:success", Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, "signInWithFacebook:success")
//                    val intent7 = Intent(this, HomepageActivity::class.java)
//                    startActivity(intent7)
//                    val user = auth.currentUser
//                    updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithFacebook:failure", task.exception)
//                    Toast.makeText(
//                        baseContext, "signInWithFacebook:failure",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    updateUI(null)
//                }
//            }
//    }
//
//
//

    private fun updateUI(user: FirebaseUser?) {
        if(user == null) {
            return
        }
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val RC_SIGN_IN = 9001
    }

}








