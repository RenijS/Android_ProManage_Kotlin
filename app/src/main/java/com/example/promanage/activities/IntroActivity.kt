package com.example.promanage.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.ActionBar
import com.example.promanage.R

class IntroActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val btSignUp = findViewById<Button>(R.id.btSignUp)
        btSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val btSignIn = findViewById<Button>(R.id.btSignIn)
        btSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}