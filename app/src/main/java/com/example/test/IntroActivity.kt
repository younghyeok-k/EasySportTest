package com.example.test

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.test.api.AllApi
import com.example.test.api.LoginApi
import com.example.test.api.Mlogin
import com.example.test.api.SportService
import com.example.test.application.SharedManager
import com.example.test.databinding.ActivityIntroBinding
import com.example.test.dto.SportDto
import com.example.test.model.User
import com.example.test.model.loginPost
import com.example.test.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

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
//            viewModel.getLogin(user)
//
//
//
//            viewModel.getJoin(user)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            Log.d("회원가입",viewModel.livejoinList.value.toString())
            getLoginAPI(user)
        }
//        viewModel.liveloginList.observe(this, Observer {
//            Log.d("로그인성공", it.execute().body()!!.msg)
//            sharedManager.saveCurrentUser(it.execute().body()!!.data)
//            Toast.makeText(this,it.execute().body()!!.msg,Toast.LENGTH_SHORT)
//
//            Log.d("로그인성공", sharedManager.getCurrentUser().email.toString())
//        })
    }

//    private fun getSportListFromAPI(user: User) {
//        val retrofit = Retrofit.Builder()
////        http://192.168.48.1:8080/
//            .baseUrl("https://run.mocky.io")
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(LoginApi::class.java).also {
//            it.getLogin(user)
//                .enqueue(object : Callback<loginPost> {
//                    @SuppressLint("SetTextI18n")
//                    override fun onResponse(call: Call<loginPost>, response: Response<loginPost>) {
//
//                        response.body()?.let { dto ->
//Log.d("로그인",dto.msg)
//                        }
//                    }
//
//                    override fun onFailure(call: Call<loginPost>, t: Throwable) {
//                        Log.d("로그인","실패")
//                    }
//                })
//        }
//    }

    private fun getLoginAPI(user: User) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Mlogin::class.java).also {
            it.getloginList()
                .enqueue(object : Callback<loginPost> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<loginPost>, response: Response<loginPost>) {
                        if (response.isSuccessful.not()) {

                            return
                        }
                        response.body()?.let { dto ->
                            Log.d("retrofit", "통신 성공")
                            sharedManager.saveCurrentUser(user)
                            Log.d("shared", sharedManager.getCurrentUser().username.toString())
                        }
                    }

                    override fun onFailure(call: Call<loginPost>, t: Throwable) {
                        Log.d("retrofit", "통신 실패")
                    }
                })
        }
    }
}



