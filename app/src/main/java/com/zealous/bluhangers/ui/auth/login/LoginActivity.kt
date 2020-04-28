package com.zealous.bluhangers.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.R
import com.zealous.bluhangers.databinding.ActivityLoginBinding
import com.zealous.bluhangers.ui.auth.AuthViewModel
import com.zealous.bluhangers.ui.auth.AuthViewModelFactory
import com.zealous.bluhangers.ui.auth.register.RegisterActivity
import com.zealous.bluhangers.ui.sync.SyncActivity
import com.zealous.bluhangers.utils.LoadingDialog
import com.zealous.bluhangers.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), LoginUserListener, KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.loginListener = this

        // Set Loading
        loadingDialog = LoadingDialog(this)
    }

    override fun onLoginStart() {
        loadingDialog.showDialog()
    }

    override fun onLoginSuccess(message: String) {
        loadingDialog.hideDialog()
        toast(message)
        Intent(this@LoginActivity, SyncActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }

    override fun onLoginFailure(error: String) {
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

    override fun toRegister() {
        Intent(this@LoginActivity, RegisterActivity::class.java).also {
            startActivity(it)
        }
        finish()
    }

}
