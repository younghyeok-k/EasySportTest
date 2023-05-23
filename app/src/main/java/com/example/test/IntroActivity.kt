package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.application.SharedManager
import com.example.test.databinding.ActivityIntroBinding
import com.example.test.model.User
import com.example.test.viewmodel.MainViewModel

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var viewModel: MainViewModel
    private val sharedManager: SharedManager by lazy { SharedManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        binding.btnlogin.setOnClickListener {

            val user = User()
            user.username = "asd2"
            user.password = "asd2"
            viewModel.getLogin(user)



            viewModel.getJoin(user)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.d("회원가입",viewModel.livejoinList.value.toString())

        }
        viewModel.liveloginList.observe(this, Observer {
            Log.d("로그인성공", it.execute().body()!!.msg)
            sharedManager.saveCurrentUser(it.execute().body()!!.data)
            Toast.makeText(this,it.execute().body()!!.msg,Toast.LENGTH_SHORT)

            Log.d("로그인성공", sharedManager.getCurrentUser().email.toString())
        })
    }
}



