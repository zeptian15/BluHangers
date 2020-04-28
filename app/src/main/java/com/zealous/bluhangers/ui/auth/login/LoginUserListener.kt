package com.zealous.bluhangers.ui.auth.login

interface LoginUserListener {

    fun onLoginStart()
    fun onLoginSuccess(message: String)
    fun onLoginFailure(error: String)
    fun onError(error: String)
    fun onFinish()
    fun toRegister()
}