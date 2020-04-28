package com.zealous.bluhangers.ui.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.R
import com.zealous.bluhangers.databinding.ActivityRegisterBinding
import com.zealous.bluhangers.ui.auth.AuthViewModel
import com.zealous.bluhangers.ui.auth.AuthViewModelFactory
import com.zealous.bluhangers.ui.auth.login.LoginActivity
import com.zealous.bluhangers.utils.LoadingDialog
import com.zealous.bluhangers.utils.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(),
    RegisterUserListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.registerListener = this

        loadingDialog = LoadingDialog(this)
    }

    override fun onRegisterStart() {
        loadingDialog.showDialog()
    }

    override fun onRegisterSuccess(message: String) {
        loadingDialog.hideDialog()
        toast(message)
        Intent(this@RegisterActivity, LoginActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }

    override fun onRegisterFailure(error: String) {
        loadingDialog.hideDialog()
        toast(error)
    }

    override fun onError(error: String) {
        loadingDialog.hideDialog()
        toast(error)
    }

    override fun onFinish() {
        finish()
    }

    override fun toLogin() {
        Intent(this@RegisterActivity, LoginActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }
}
