package com.example.test.application

import android.content.Context
import android.content.SharedPreferences
import com.example.test.model.User
import com.example.test.application.PreferenceHelper.set
import com.example.test.application.PreferenceHelper.get


object SharedManager {
    private lateinit var prefs: SharedPreferences
    private lateinit var instance: SharedManager

    fun init(context: Context) {
        prefs = PreferenceHelper.defaultPrefs(context)
    }

    fun getInstance(): SharedManager {
        if (!::instance.isInitialized) {
            instance = SharedManager
        }
        return instance
    }

    fun saveCurrentUser(user: User) {
        prefs["username"] = user.username!!
        prefs["password"] = user.password!!
    }

    fun getCurrentUser(): User {
        return User().apply {
            username = prefs["username", ""]
            password = prefs["password", ""]
        }
    }

    fun saveBearerToken(token: String) {
        prefs["bearerToken"] = token
    }

    fun getBearerToken(): String {
        return prefs["bearerToken", ""]
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
