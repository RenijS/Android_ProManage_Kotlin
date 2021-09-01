package com.example.promanage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.promanage.R
import com.example.promanage.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setUpActionBar()

        val btSignIn: Button = findViewById(R.id.btSignIn2)
        btSignIn.setOnClickListener {
            val etEmail: EditText = findViewById(R.id.etEmail)
            val email = etEmail.text.toString().trim { it<= ' '}
            val etPassword: EditText = findViewById(R.id.etPassword)
            val password = etPassword.text.toString().trim { it<= ' '}

            if(email.isEmpty() || password.isEmpty()){
                showErrorSnackBar("Please enter all details")
            }
            else{
                signInWithEmailAndPassword(email, password)
            }

        }
    }
    private fun setUpActionBar(){
        val toolbarSignUpActivity = findViewById<Toolbar>(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbarSignUpActivity)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        toolbarSignUpActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun signInWithEmailAndPassword(email: String, password: String){
        showProgressDialog("Please wait")
        val currentUser :FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (currentUser == null){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    task ->
                hideProgressDialog()
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                    } else{
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        Log.w("Sign In", "signInEP: failure", task.exception)
                    }
            }
        }
    }
}