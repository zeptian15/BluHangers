package com.zealous.bluhangers.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.R
import androidx.lifecycle.Observer
import com.zealous.bluhangers.ui.auth.LoginActivity
import com.zealous.bluhangers.ui.sync.SyncActivity
import com.zealous.bluhangers.utils.Constants.SPLASH_TIMEOUT
import com.zealous.bluhangers.utils.toast

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        // Run function
        viewModel.checkUser()

        Handler().postDelayed({

            // Check User
            viewModel.user.observe(this, Observer { user ->
                if(user != null){
                    user.id_outlet?.let { checkOutletStatus(it) }
                } else {
                    Intent(this@SplashActivity, LoginActivity::class.java).also {
                        startActivity(it)
                    }
                    finish()
                }

            })

        }, SPLASH_TIMEOUT)
    }

    private fun checkOutletStatus(id: Int){
        if(id == 0){
            Intent(this@SplashActivity, SyncActivity::class.java).also {
                startActivity(it)
            }
            finish()
        } else {
            toast("Berhasil masuk ke Dashboard")
        }
    }
}
