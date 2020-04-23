package com.zealous.bluhangers.ui.auth

import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.zealous.bluhangers.data.models.User
import com.zealous.bluhangers.ui.sync.SyncActivity
import com.zealous.bluhangers.utils.Constants.NODE_USERS

class AuthViewModel : ViewModel() {

    private val dbAuth = FirebaseAuth.getInstance()
    private val dbFirebase = FirebaseDatabase.getInstance().getReference(NODE_USERS)

    // User
    var fullName: String? = null
    var nickname: String? = null
    var email: String? = null
    var password: String? = null
    var rePassword: String? = null

    var authListener: AuthListener? = null


    fun toRegister(view: View){
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
        authListener?.onFinish()
    }

    fun toLogin(view: View){
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
        authListener?.onFinish()
    }

    fun toSync(view: View){
        Intent(view.context, SyncActivity::class.java).also {
            view.context.startActivity(it)
        }
        authListener?.onFinish()
    }

    fun registerUser(view: View){
        authListener?.onStarted()
        if(fullName.isNullOrEmpty()){
            authListener?.onFailure("Tolong masukan nama lengkap")
            return
        }
        if(nickname.isNullOrEmpty()){
            authListener?.onFailure("Tolong masukan nama panggilan")
            return
        }
        if(email.isNullOrEmpty()){
            authListener?.onFailure("Tolong masukan email")
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            authListener?.onFailure("Tolong masukan email yang valid")
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure("Tolong masukan kata sandi")
            return
        }
        if(password != rePassword){
            authListener?.onFailure("Kata sandi tidak cocok")
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
                user.id_outlet = 0
                user.role = "Admin"
                dbFirebase.child(dbAuth.currentUser!!.uid).setValue(user).addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        authListener?.onSuccess("Berhasil melakukan registrasi")
                        Intent(view.context, LoginActivity::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            view.context.startActivity(it)
                        }
                        authListener?.onFinish()
                    } else {
                        authListener?.onFailure("Gagal melakukan registrasi")
                    }
                }
            } else {
                authListener?.onFailure(task.exception?.message.toString())
            }
        }
    }

    fun loginUser(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty()){
            authListener?.onFailure("Tolong masukan email")
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            authListener?.onFailure("Tolong masukan email yang valid")
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure("Tolong masukan kata sandi")
            return
        }

        dbAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                authListener?.onSuccess("Berhasil login")
                Intent(view.context, SyncActivity::class.java).also {
                    view.context.startActivity(it)
                }
                authListener?.onFinish()
            } else {
                authListener?.onFailure("Email / Password salah")
            }
        }
    }

}