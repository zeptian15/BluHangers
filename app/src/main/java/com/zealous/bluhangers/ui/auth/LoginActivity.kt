package com.zealous.bluhangers.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.zealous.bluhangers.R
import com.zealous.bluhangers.databinding.ActivityLoginBinding
import com.zealous.bluhangers.utils.LoadingDialog
import com.zealous.bluhangers.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), AuthListener
{

    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.authListener = this

        // Set Loading
        loadingDialog = LoadingDialog(this)
    }

    override fun onStarted() {
        loadingDialog.showDialog()
    }

    override fun onSuccess(message: String) {
        loadingDialog.hideDialog()
        toast(message)
    }


    override fun onFailure(message: String) {
        loadingDialog.hideDialog()
        toast(message)
    }

    override fun onFinish() {
        finish()
    }
}
