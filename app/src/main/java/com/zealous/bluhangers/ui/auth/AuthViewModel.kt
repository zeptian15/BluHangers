package com.zealous.bluhangers.ui.auth

import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.zealous.bluhangers.data.models.User
import com.zealous.bluhangers.ui.sync.SyncActivity
import com.zealous.bluhangers.utils.Constants.DEFAULT_OUTLET
import com.zealous.bluhangers.utils.Constants.ERROR_EMAIL
import com.zealous.bluhangers.utils.Constants.ERROR_FULLNAME
import com.zealous.bluhangers.utils.Constants.ERROR_LOGIN
import com.zealous.bluhangers.utils.Constants.ERROR_NICKNAME
import com.zealous.bluhangers.utils.Constants.ERROR_PASSWORD
import com.zealous.bluhangers.utils.Constants.ERROR_REGISTER
import com.zealous.bluhangers.utils.Constants.ERROR_RE_PASSWORD
import com.zealous.bluhangers.utils.Constants.ERROR_VALIDATE_EMAIL
import com.zealous.bluhangers.utils.Constants.NODE_USERS
import com.zealous.bluhangers.utils.Constants.ROLE_ADMIN
import com.zealous.bluhangers.utils.Constants.SUCCESS_LOGIN
import com.zealous.bluhangers.utils.Constants.SUCCESS_REGISTER

class AuthViewModel : ViewModel() {

    // Initialize Firebase Database and Firebase Auth Connection
    private val dbAuth = FirebaseAuth.getInstance()
    private val dbFirebase = FirebaseDatabase.getInstance().getReference(NODE_USERS)

    // Initialize Variabel for User
    var fullName: String? = null
    var nickname: String? = null
    var email: String? = null
    var password: String? = null
    var rePassword: String? = null

    // Initialize Listener for View
    var authListener: AuthListener? = null

    // Redirect User to Register Activity
    fun toRegister(view: View){
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
        authListener?.onFinish()
    }

    // Redirect User to Login Activity
    fun toLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
        authListener?.onFinish()
    }

    // Redirect User to Sync Activity
    fun toSync(view: View){
        Intent(view.context, SyncActivity::class.java).also {
            view.context.startActivity(it)
        }
        authListener?.onFinish()
    }

    // Action if Register Button is Clicked
    fun registerUser(view: View){
        // Validate Input
        authListener?.onStarted()
        if(fullName.isNullOrEmpty()){
            authListener?.onFailure(ERROR_FULLNAME)
            return
        }
        if(nickname.isNullOrEmpty()){
            authListener?.onFailure(ERROR_NICKNAME)
            return
        }
        if(email.isNullOrEmpty()){
            authListener?.onFailure(ERROR_EMAIL)
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email!!).matches()){
            authListener?.onFailure(ERROR_VALIDATE_EMAIL)
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure(ERROR_PASSWORD)
            return
        }
        if(password != rePassword){
            authListener?.onFailure(ERROR_RE_PASSWORD)
            return
        }

        // Save Users to FirebaseAuth
        dbAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
            if(task.isSuccessful){

                // Save Users to FirebaseDatabase
                val user = User()
                user.fullName = fullName
                user.nickName = nickname
                user.email = email
                user.id_outlet = DEFAULT_OUTLET
                user.role = ROLE_ADMIN

                // Insert data to FIrebase Database
                dbFirebase.child(dbAuth.currentUser!!.uid).setValue(user).addOnCompleteListener{task ->

                    // Check If Task Is Successful
                    if(task.isSuccessful){
                        authListener?.onSuccess(SUCCESS_REGISTER)
                        // Redirect to Login Activity
                        Intent(view.context, LoginActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            view.context.startActivity(it)
                        }
                        authListener?.onFinish()
                    } else {
                        authListener?.onFailure(ERROR_REGISTER)
                    }
                }
            } else {
                val error = task.exception?.message.toString()
                authListener?.onFailure(error)
            }
        }
    }

    // Action if Login Button is Clicked
    fun loginUser(view: View){
        // Validate Input
        authListener?.onStarted()
        if(email.isNullOrEmpty()){
            authListener?.onFailure(ERROR_EMAIL)
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email!!).matches()){
            authListener?.onFailure(ERROR_VALIDATE_EMAIL)
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure(ERROR_PASSWORD)
            return
        }

        // Sinin with email and password
        dbAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener{ task ->
            // Check if task is successful
            if(task.isSuccessful){
                authListener?.onSuccess(SUCCESS_LOGIN)
                // Redirect to Sync Activity
                Intent(view.context, SyncActivity::class.java).also {
                    view.context.startActivity(it)
                }
                authListener?.onFinish()
            } else {
                authListener?.onFailure(ERROR_LOGIN)
            }
        }
    }

}