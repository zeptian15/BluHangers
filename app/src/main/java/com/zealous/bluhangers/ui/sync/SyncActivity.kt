package com.zealous.bluhangers.ui.sync

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.R
import com.zealous.bluhangers.databinding.ActivitySyncBinding
import com.zealous.bluhangers.ui.sync.outlet.SyncOutletActivity
import com.zealous.bluhangers.utils.toast
import kotlinx.android.synthetic.main.activity_sync.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SyncActivity : AppCompatActivity(), SyncListener, KodeinAware {

    override val kodein by kodein()
    private val factory: SyncViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySyncBinding = DataBindingUtil.setContentView(this, R.layout.activity_sync)
        val viewModel= ViewModelProvider(this, factory).get(SyncViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.syncListener = this
        viewModel.fetchUser()

    }

    override fun onSuccess(message: String) {
        toast(message)
    }

    override fun onFailure(error: String) {
        toast(error)
    }

    override fun onOutletExist() {
        toast("Berhasil masuk ke Dashboard")
    }

    override fun onOutletNotExist() {
        toast("Silahkan pilih outlet terlebih dahulu")
    }

    override fun toSyncOutlet() {
        Intent(this@SyncActivity, SyncOutletActivity::class.java).also {
            startActivity(it)
        }
    }


}
