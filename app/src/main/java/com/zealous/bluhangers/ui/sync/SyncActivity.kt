package com.zealous.bluhangers.ui.sync

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.zealous.bluhangers.R
import com.zealous.bluhangers.ui.auth.LoginActivity
import com.zealous.bluhangers.utils.Constants.NODE_USERS
import com.zealous.bluhangers.utils.toast
import kotlinx.android.synthetic.main.activity_sync.*

class SyncActivity : AppCompatActivity() {

    private lateinit var viewModel: SyncViewModel
    private var dbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        // Viewmodel
        viewModel = ViewModelProvider(this).get(SyncViewModel::class.java)

        // Fecth User
        viewModel.fetchUser()

        // Observe User
        viewModel.user.observe(this, Observer { user ->
            if(user.id_outlet == 0){
                val message = "Hallo, ${user.nickName} pilih dulu outletnya yaa!"
                toast(message)
            } else {
                toast("Ente masuk dashboard gan")
            }
        })

        btn_sinkronisasi_outlet.setOnClickListener {
            dbAuth.signOut()
            Intent(this@SyncActivity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
            finish()
        }
    }
}
