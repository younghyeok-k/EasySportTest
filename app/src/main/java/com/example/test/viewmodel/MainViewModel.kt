package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.test.api.AllApi
import com.example.test.api.LoginApi
import com.example.test.api.RetrofitInstance
import com.example.test.model.Data
import com.example.test.model.User
import com.example.test.model.loginPost
import kotlinx.coroutines.launch
import retrofit2.Call


class MainViewModel : ViewModel() {


    private val retrofitInstance = RetrofitInstance.getInstance().create(LoginApi::class.java)

    private var _loginList = MutableLiveData<Call<loginPost>>()
    val liveloginList: LiveData<Call<loginPost>>
        get() = _loginList

    fun getLogin(user: User) = viewModelScope.launch {
        val post1 = retrofitInstance.getLogin(user)

//sharedper 값 넘겨줘야함
//        _loginList.value = post1
    }


//    // 회원가입
//    private val retrofitInstance2 = RetrofitInstance.getInstance().create(JoinApi::class.java)
//
//    private var _joinList = MutableLiveData<List<joinPost>>()
//    val livejoinList: LiveData<List<joinPost>>
//        get() = _joinList
//
//    fun getJoin(user: User) = viewModelScope.launch {
//        val post1 = retrofitInstance2.getJoin(user)
//        _joinList.value = post1
//    }


    //userall
    private val retrofitInstance3 = RetrofitInstance.getInstance().create(AllApi::class.java)

    private var _allList = MutableLiveData<List<Data>>()
    val liveallList: LiveData<List<Data>>
        get() = _allList

    fun getall() = viewModelScope.launch {
        val post1 = retrofitInstance3.getall()

        _allList.value = post1
    }


}