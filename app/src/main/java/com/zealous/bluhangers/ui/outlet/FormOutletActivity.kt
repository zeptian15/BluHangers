package com.zealous.bluhangers.ui.outlet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.zealous.bluhangers.R
import com.zealous.bluhangers.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_form_outlet.*

class FormOutletActivity : AppCompatActivity() {

    private var dbAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_outlet)

        btn_logout.setOnClickListener {
            dbAuth.signOut()
            Intent(this@FormOutletActivity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
