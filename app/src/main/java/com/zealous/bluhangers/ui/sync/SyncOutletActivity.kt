package com.zealous.bluhangers.ui.sync

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zealous.bluhangers.R
import kotlinx.android.synthetic.main.activity_sync_outlet.*

class SyncOutletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_outlet)

        btn_kembali.setOnClickListener {
            finish()
        }
    }
}
