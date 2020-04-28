package com.zealous.bluhangers.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealous.bluhangers.data.model.User
import com.zealous.bluhangers.utils.Constants
import java.lang.Exception

class SplashViewModel : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    // Firebase Instance
    private val dbUser = FirebaseDatabase.getInstance().getReference(Constants.NODE_USERS)
    private val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun checkUser(){
        dbUser.child(currentUserUID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                // Do Nothing
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                _user.value = snapshot.getValue(User::class.java)

            }

        })
    }
}