package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)


        val sign=findViewById<TextView>(R.id.back)

        sign.setOnClickListener{
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}