package com.zealous.bluhangers.ui.outlet

import android.R.string
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.R
import com.zealous.bluhangers.databinding.ActivityFormOutletBinding
import com.zealous.bluhangers.utils.LoadingDialog
import com.zealous.bluhangers.utils.toast
import kotlinx.android.synthetic.main.activity_form_outlet.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class FormOutletActivity : AppCompatActivity(), OutletListener, KodeinAware {

    override val kodein by kodein()
    private val factory: OutletViewModelFactory by instance()
    private var imgPath: Uri? = null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var viewModel: OutletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFormOutletBinding = DataBindingUtil.setContentView(this, R.layout.activity_form_outlet)
        viewModel = ViewModelProvider(this, factory).get(OutletViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.outletListener = this

        loadingDialog = LoadingDialog(this)

//        btn_logout.setOnClickListener {
//            dbAuth.signOut()
//            Intent(this@FormOutletActivity, LoginActivity::class.java).also {
//                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                startActivity(it)
//            }
//            finish()
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            imgPath = data.data
            img_image_outlet.setImageURI(data.data)
            val file = System.currentTimeMillis().toString() + "." + imgPath?.let { getFileExtension(it) }
            file.let { viewModel.setImageName(it) }
            imgPath?.let { viewModel.setImagePath(it) }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onStarted() {
        loadingDialog.showDialog()
    }

    override fun onSuccess(message: String) {
        loadingDialog.hideDialog()
        toast(message)
        finish()
    }

    override fun onFailure(error: String) {
        loadingDialog.hideDialog()
        toast(error)
    }

    override fun onError(error: String) {
        loadingDialog.hideDialog()
        toast(error)
    }

    override fun onPickImage() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes =
                arrayOf("image/jpeg", "image/png", "image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, 0)
        }
    }

    override fun onFinish() {
        finish()
    }

    private fun getFileExtension(uri: Uri): String? {
        val cr = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri))
    }
}
