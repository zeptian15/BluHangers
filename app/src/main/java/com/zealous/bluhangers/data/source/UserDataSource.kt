package com.zealous.bluhangers.data.source

import com.zealous.bluhangers.data.model.User

interface UserDataSource {

    fun login(user: User, callback: loginCallback)
    fun register(user: User, callback: registerCallback)

    interface loginCallback {
        fun onLoginSuccess(message: String)
        fun onLoginError(error: String)
    }

    interface registerCallback {
        fun onRegisterSuccess(message: String)
        fun onRegisterError(error: String)
    }
}