package com.example.promanage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.promanage.R
import com.example.promanage.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setUpActionBar()

        val btSignUp: Button = findViewById(R.id.btSignUp2)
        btSignUp.setOnClickListener {
            registerUser()
        }
    }

    private fun setUpActionBar(){
        val toolbarSignUpActivity = findViewById<Toolbar>(R.id.toolbar_sign_up_activity)
        setSupportActionBar(toolbarSignUpActivity)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        toolbarSignUpActivity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser(){
        val etName: EditText = findViewById(R.id.etName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val name: String = etName.text.toString().trim{it <= ' '}
        val  email: String = etEmail.text.toString().trim{it <= ' '}
        val password = etPassword.text.toString().trim{it <= ' '}

        if (validateForm(name, email, password)){
            showProgressDialog("Please Wait")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                hideProgressDialog()
                if (task.isComplete) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!

                    Toast.makeText(this, "Registered email is $registeredEmail", Toast.LENGTH_LONG)
                        .show()

                    FirebaseAuth.getInstance().signOut()
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show()
                    Log.w("Sign Up", "signUpEP: failure", task.exception)
                }
            }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean{
        return when{
            TextUtils.isEmpty(name) ->{
                showErrorSnackBar("Please Enter a Name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password")
                false
            } else ->{
                true
            }

        }
    }
}