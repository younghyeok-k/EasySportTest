package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.example.LoginResponse
import com.example.test.api.*
import com.example.test.application.SharedManager
import com.example.test.application.SharedManager.saveBearerToken
import com.example.test.databinding.ActivityIntroBinding
import com.example.test.model.User
import com.example.test.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedManager: SharedManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        SharedManager.init(this)
        sharedManager = SharedManager.getInstance()

        binding.btnlogin.setOnClickListener {

            val user = User()
            user.username = "asd2"
            user.password = "asd2"

//
//
//
//            viewModel.getJoin(user)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            Log.d("회원가입",viewModel.livejoinList.value.toString())
            getLoginAPI(user)
        }
        viewModel.liveloginList.observe(this, Observer {
//            Log.d("로그인성공", it.execute().body()!!.msg)
//            Toast.makeText(this,it.execute().body()!!.msg,Toast.LENGTH_SHORT)

        })
    }
    private fun getLoginAPI(user: User) {

            RetrofitInstance.retrofit.create(LoginApi::class.java)
                .getLogin(user)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            val body = response.body()
                            Log.d("Intro", "success");
                            response.body()?.let { dto ->

                                sharedManager.saveCurrentUser(user)


                                Log.d("shared", dto.msg.toString())

                            }

                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
        }

}



