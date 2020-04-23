package com.zealous.bluhangers.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealous.bluhangers.utils.Constants.SPLASH_TIMEOUT
import com.zealous.bluhangers.R
import com.zealous.bluhangers.data.models.User
import com.zealous.bluhangers.ui.auth.LoginActivity
import com.zealous.bluhangers.ui.sync.SyncActivity
import com.zealous.bluhangers.utils.Constants.NODE_USERS
import com.zealous.bluhangers.utils.toast
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val dbAuth = FirebaseAuth.getInstance().currentUser
    private val dbUser = FirebaseDatabase.getInstance().getReference(NODE_USERS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Check If User Has Login or not
            checkLogin()
        }, SPLASH_TIMEOUT)
    }

    private fun checkLogin(){
        if(dbAuth != null){
            val currentUserUid = dbAuth.uid
            checkIdOutletUser(currentUserUid)
        } else {
            Intent(this@SplashActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun checkIdOutletUser(uid: String){
        dbUser.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                // Do Nothing
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if(user?.id_outlet == 0){
                    Intent(this@SplashActivity, SyncActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                } else {
                    toast("Berhasil masuk Dashboard")
                }
            }

        })
    }
}
