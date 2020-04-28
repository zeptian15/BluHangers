package com.zealous.bluhangers.ui.auth.register


interface RegisterUserListener {
    fun onRegisterStart()
    fun onRegisterSuccess(message: String)
    fun onRegisterFailure(error: String)
    fun onError(error: String)
    fun onFinish()
    fun toLogin()
}