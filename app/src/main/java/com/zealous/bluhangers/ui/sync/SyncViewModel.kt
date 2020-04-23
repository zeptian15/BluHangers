package com.zealous.bluhangers.ui.sync

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealous.bluhangers.data.models.User
import com.zealous.bluhangers.utils.Constants.NODE_USERS
import com.zealous.bluhangers.utils.toast
import java.lang.Exception

class SyncViewModel : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    // Firebase Instance
    private val dbUser = FirebaseDatabase.getInstance().getReference(NODE_USERS)
    private val currentUserUID = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun fetchUser() {
        dbUser.child(currentUserUID)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    // Do Nothing
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        _user.value = snapshot.getValue(User::class.java)
                    }
                }

            })
    }
}