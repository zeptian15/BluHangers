package com.zealous.bluhangers.ui.sync

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zealous.bluhangers.R
import com.zealous.bluhangers.ui.outlet.FormOutletActivity
import kotlinx.android.synthetic.main.activity_sync_outlet.*

class SyncOutletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_outlet)

        btn_kembali.setOnClickListener {
            finish()
        }

        btn_register.setOnClickListener {
            Intent(this@SyncOutletActivity, FormOutletActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
