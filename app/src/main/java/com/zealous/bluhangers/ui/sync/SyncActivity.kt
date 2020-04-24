package com.zealous.bluhangers.ui.sync

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.zealous.bluhangers.R
import com.zealous.bluhangers.ui.auth.LoginActivity
import com.zealous.bluhangers.ui.outlet.FormOutletActivity
import com.zealous.bluhangers.utils.toast
import kotlinx.android.synthetic.main.activity_sync.*

class SyncActivity : AppCompatActivity() {

    private lateinit var viewModel: SyncViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync)

        // Viewmodel
        viewModel = ViewModelProvider(this).get(SyncViewModel::class.java)

        // Fecth User
        viewModel.fetchUser()

        // Observe User
        viewModel.user.observe(this, Observer { user ->
            if(user != null){
                user.id_outlet?.let { checkOutletStatus(it) }
            } else {
                toast("Berhasil masuk dashboard")
            }
        })

        btn_sinkronisasi_outlet.setOnClickListener {
            Intent(this@SyncActivity, SyncOutletActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun checkOutletStatus(id: Int){
        if(id == 0){
            toast("Silahkan pilih outlet terlebih dahulu")
        } else {
            toast("Berhasil masuk ke Dashboard")
        }
    }
}
