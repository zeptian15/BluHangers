package com.zealous.bluhangers.ui.sync.outlet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zealous.bluhangers.R
import com.zealous.bluhangers.databinding.ActivitySyncOutletBinding
import com.zealous.bluhangers.ui.outlet.FormOutletActivity
import com.zealous.bluhangers.ui.sync.SyncViewModel
import com.zealous.bluhangers.ui.sync.SyncViewModelFactory
import com.zealous.bluhangers.utils.LoadingDialog
import com.zealous.bluhangers.utils.toast
import kotlinx.android.synthetic.main.activity_sync_outlet.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SyncOutletActivity : AppCompatActivity(),
    SyncOutletListener, KodeinAware {

    override val kodein by kodein()
    private val factory: SyncViewModelFactory by instance()
    private lateinit var adapterOutlet: SyncOutletAdapter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivitySyncOutletBinding = DataBindingUtil.setContentView(this, R.layout.activity_sync_outlet)
        val viewModel = ViewModelProvider(this, factory).get(SyncViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.syncOutletListener = this

        loadingDialog = LoadingDialog(this)
        viewModel.loadOutlet()
        viewModel.loadRealtime()
        loadingDialog.showDialog()

        adapterOutlet = SyncOutletAdapter()
        setupRecyclerview()

        viewModel.outlets.observe(this, Observer {
            if (it.size > 0){
                loadingDialog.hideDialog()
                showEmptyView(false)
                adapterOutlet.setListOutlet(it)
            } else {
                loadingDialog.hideDialog()
                showEmptyView(true)
            }
        })

        viewModel.outlet.observe(this, Observer {
            if (it != null){
                adapterOutlet.addOutlet(it)
                checkItem(adapterOutlet.getItem())
            }
        })
    }

    private fun setupRecyclerview(){
        rv_outlet.apply {
            layoutManager = LinearLayoutManager(this@SyncOutletActivity)
            setHasFixedSize(true)
            adapter = adapterOutlet
        }
    }

    override fun onError(error: String) {
        toast(error)
    }

    override fun onFinish() {
        finish()
    }

    override fun toRegister() {
        Intent(this@SyncOutletActivity, FormOutletActivity::class.java).also {
            startActivity(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun checkItem(item: Int){
        if(item > 0){
            showEmptyView(false)
        } else {
            showEmptyView(true)
        }
    }

    private fun showEmptyView(show: Boolean){
        if(show){
            empty_data_view.visibility = View.VISIBLE
        } else {
            empty_data_view.visibility = View.GONE
        }
    }
}
