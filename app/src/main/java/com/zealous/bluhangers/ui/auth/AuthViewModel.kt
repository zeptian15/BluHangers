package com.zealous.bluhangers.ui.auth

import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import com.zealous.bluhangers.data.UserRepository
import com.zealous.bluhangers.data.model.User
import com.zealous.bluhangers.data.source.UserDataSource
import com.zealous.bluhangers.ui.auth.login.LoginActivity
import com.zealous.bluhangers.ui.auth.login.LoginUserListener
import com.zealous.bluhangers.ui.auth.register.RegisterUserListener
import com.zealous.bluhangers.ui.auth.register.RegisterActivity
import com.zealous.bluhangers.ui.sync.SyncActivity
import com.zealous.bluhangers.utils.Constants.DEFAULT_OUTLET
import com.zealous.bluhangers.utils.Constants.ERROR_EMAIL
import com.zealous.bluhangers.utils.Constants.ERROR_FULLNAME
import com.zealous.bluhangers.utils.Constants.ERROR_NICKNAME
import com.zealous.bluhangers.utils.Constants.ERROR_PASSWORD
import com.zealous.bluhangers.utils.Constants.ERROR_RE_PASSWORD
import com.zealous.bluhangers.utils.Constants.ERROR_VALIDATE_EMAIL
import com.zealous.bluhangers.utils.Constants.ROLE_ADMIN

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // Initialize Variabel for User
    var fullName: String? = null
    var nickname: String? = null
    var email: String? = null
    var password: String? = null
    var rePassword: String? = null

    // Initialize Listener for each view
    var loginListener: LoginUserListener? = null
    var registerListener: RegisterUserListener? = null

    fun toRegister(){
        loginListener?.toRegister()
    }

    fun toLogin(){
        registerListener?.toLogin()
    }

    // Action if Register Button is Clicked
    fun registerUser(){
        // Validate Input
        registerListener?.onRegisterStart()
        if(fullName.isNullOrEmpty()){
            registerListener?.onError(ERROR_FULLNAME)
            return
        }
        if(nickname.isNullOrEmpty()){
            registerListener?.onError(ERROR_NICKNAME)
            return
        }
        if(email.isNullOrEmpty()){
            registerListener?.onError(ERROR_EMAIL)
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email!!).matches()){
            registerListener?.onError(ERROR_VALIDATE_EMAIL)
            return
        }
        if(password.isNullOrEmpty()){
            registerListener?.onError(ERROR_PASSWORD)
            return
        }
        if(password != rePassword){
            registerListener?.onError(ERROR_RE_PASSWORD)
            return
        }

        // Set value to User
        val user = User()
        user.fullName = fullName
        user.nickName = nickname
        user.email = email
        user.password = password
        user.id_outlet = DEFAULT_OUTLET
        user.role = ROLE_ADMIN

        // Register
        repository.register(user, object : UserDataSource.registerCallback {
            override fun onRegisterSuccess(message: String) {
                registerListener?.onRegisterSuccess(message)
            }

            override fun onRegisterError(error: String) {
                registerListener?.onRegisterFailure(error)
            }

        })
    }


    fun loginUser(){
        loginListener?.onLoginStart()
        if(email.isNullOrEmpty()){
            loginListener?.onError(ERROR_EMAIL)
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email!!).matches()){
            loginListener?.onError(ERROR_VALIDATE_EMAIL)
            return
        }
        if(password.isNullOrEmpty()){
            loginListener?.onError(ERROR_PASSWORD)
            return
        }

        val user = User()
        user.email = email
        user.password = password

        repository.login(user, object: UserDataSource.loginCallback {
            override fun onLoginSuccess(message: String) {
                loginListener?.onLoginSuccess(message)
            }

            override fun onLoginError(error: String) {
                loginListener?.onLoginFailure(error)
            }
        })
    }

}