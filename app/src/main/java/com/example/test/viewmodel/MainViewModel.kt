package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.api.*

import com.example.test.model.*
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
        _loginList.value = post1
    }


    // 회원가입
    private val retrofitInstance2 = RetrofitInstance.getInstance().create(JoinApi::class.java)

    private var _joinList = MutableLiveData<Call<joinPost>>()
    val livejoinList: LiveData<Call<joinPost>>
        get() = _joinList

    fun getJoin(user: User) = viewModelScope.launch {
        val post1 = retrofitInstance2.getJoin(user)
        _joinList.value = post1
    }


    //userall
    private val retrofitInstance3 = RetrofitInstance.getInstance().create(AllApi::class.java)

    private var _allList = MutableLiveData<Call<List<Data>>>()
    val liveallList: LiveData<Call<List<Data>>>
        get() = _allList

    fun getall() = viewModelScope.launch {
        val post1 = retrofitInstance3.getall()

        _allList.value = post1
    }

    private val retrofitInstance4 = RetrofitInstance.getInstance().create(Center::class.java)

    private var _centerallList = MutableLiveData<List<SportModel2>>()
    val livecallList: LiveData<List<SportModel2>>
        get() = _centerallList

    fun getcenterall() = viewModelScope.launch {
        val post1 = retrofitInstance4.getall()

        //_centerallList.value = post1
    }


}