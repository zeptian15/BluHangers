package com.zealous.bluhangers.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.zealous.bluhangers.data.model.User
import com.zealous.bluhangers.data.source.UserDataSource
import com.zealous.bluhangers.utils.Constants.NODE_USERS

class UserRepository : UserDataSource {

    private val mDatabase = FirebaseDatabase.getInstance().getReference(NODE_USERS)
    private val mAuth = FirebaseAuth.getInstance()

    override fun login(user: User, callback: UserDataSource.loginCallback) {
        val email = user.email
        val password = user.password

        // Login
        mAuth.signInWithEmailAndPassword(email!!, password!!).addOnCompleteListener { task ->
            if(task.isSuccessful){
                callback.onLoginSuccess("Berhasil masuk")
            } else {
                callback.onLoginError("Gagal masuk")
            }
        }
    }

    override fun register(user: User, callback: UserDataSource.registerCallback) {
        val email = user.email
        val password = user.password

        // Register
        mAuth.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener {task ->
            if (task.isSuccessful){
                // Masukan data ke Database
                mDatabase.child(mAuth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                    if(task.isSuccessful){
                        callback.onRegisterSuccess("Berhasil register, silahkan login untuk melanjutkan")
                    } else {
                        callback.onRegisterError("Gagal register")
                    }
                }
            } else {
                callback.onRegisterError(task.exception?.message.toString())
            }

        }
    }
}