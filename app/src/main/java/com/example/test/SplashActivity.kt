package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.test.application.SharedManager

class SplashActivity : AppCompatActivity() {
    private val sharedManager: SharedManager by lazy { SharedManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        if (sharedManager.getCurrentUser().username.isNullOrBlank() ||sharedManager.getCurrentUser().password.isNullOrBlank()) {
            Handler().postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 2000)
        }else{
            Toast.makeText(this,"${sharedManager.getCurrentUser().username}님 자동로그인 되었습니다", Toast.LENGTH_SHORT)
            Handler().postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
    }
}