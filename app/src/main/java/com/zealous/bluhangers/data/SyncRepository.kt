package com.zealous.bluhangers.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.zealous.bluhangers.data.model.Outlet
import com.zealous.bluhangers.data.model.User
import com.zealous.bluhangers.data.source.SyncDataSource
import com.zealous.bluhangers.utils.Constants.NODE_OUTLETS
import com.zealous.bluhangers.utils.Constants.NODE_USERS

class SyncRepository : SyncDataSource {

    private val mOutletRef = FirebaseDatabase.getInstance().getReference(NODE_OUTLETS)
    private val mUserRef = FirebaseDatabase.getInstance().getReference(NODE_USERS)
    private val mAuthRef = FirebaseAuth.getInstance()

    override fun loadUser(callback: SyncDataSource.loadUserCallback) {
        val uid = mAuthRef.currentUser?.uid
        mUserRef.child(uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callback.onFailure(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user = snapshot.getValue(User::class.java)
                    callback.onSuccess(user!!)
                }
            }

        })
    }

    override fun loadRealtime(callback: SyncDataSource.loadRealtimeCallback) {
        mOutletRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                // DO Nothing
            }

            override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {
                // DO Nothing
            }

            override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
                val outlet = snapshot.getValue(Outlet::class.java)
                outlet?.id = snapshot.key
                callback.onUpdated(outlet!!)
            }

            override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                val outlet = snapshot.getValue(Outlet::class.java)
                outlet?.id = snapshot.key
                callback.onAdded(outlet!!)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val outlet = snapshot.getValue(Outlet::class.java)
                outlet?.id = snapshot.key
                outlet?.isDeleted = true
                callback.onAdded(outlet!!)
            }

        })
    }


}