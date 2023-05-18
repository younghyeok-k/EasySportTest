package com.example.test.application

import android.content.Context
import android.content.SharedPreferences
import com.example.test.model.Data
import com.example.test.model.User
import com.example.test.model.loginPost
import java.time.LocalDateTime
import com.example.test.application.PreferenceHelper.set
import com.example.test.application.PreferenceHelper.get
class SharedManager(context: Context) {

    private val prefs: SharedPreferences = PreferenceHelper.defaultPrefs(context)

    fun saveCurrentUser(user: Data) {
        prefs["id"] = user.id!!
        prefs["username"] = user.username!!
        prefs["password"] = user.password!!
        prefs["email"] = user.email!!
        prefs["role"] = user.role!!
        prefs["updateAt"] = user.updateAt!!
        prefs["createdAt"] = user.createdAt!!
    }



    fun getCurrentUser(): Data {
        return Data().apply {
            id = prefs["id", 0]
            username = prefs["username", ""]
            password = prefs["password", ""]
            email = prefs["email", ""]
            role = prefs["role", ""]
            updateAt = prefs["updateAt",]
            createdAt = prefs["createdAt", ]
        }
    }

}


