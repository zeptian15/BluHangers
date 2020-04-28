package com.zealous.bluhangers.ui.outlet

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.zealous.bluhangers.data.OutletRepository
import com.zealous.bluhangers.data.model.Outlet
import com.zealous.bluhangers.data.source.OutletDataSource
import com.zealous.bluhangers.utils.Constants.DEFAULT_IMAGE

class OutletViewModel(
    private val repository: OutletRepository
) : ViewModel() {
    var name: String? = null
    var address: String? = null
    var phone: String? = null
    var image: Uri? = null
    var imageNameFromUri: String? = null

    var outletListener: OutletListener? = null

    fun setImagePath(imagePath: Uri) {
        this.image = imagePath
    }

    fun setImageName(imageUriName: String){
        this.imageNameFromUri = imageUriName
    }

    fun pickImage() {
        outletListener?.onPickImage()
    }

    fun onBack(){
        outletListener?.onFinish()
    }

    // Initialize Firebase Storage and Firebase Database
    fun addOutlet() {
        outletListener?.onStarted()
        if (name.isNullOrEmpty()) {
            outletListener?.onError("Tolong masukan nama")
            return
        }
        if (phone.isNullOrEmpty()) {
            outletListener?.onError("Tolong masukan nomor telepon")
            return
        }
        if (address.isNullOrEmpty()) {
            outletListener?.onError("Tolong masukan alamat")
            return
        }
        if (image == null) {
            outletListener?.onError("Tolong pilih gambar")
        }

        // Set Outlet Attr
        val outlet = Outlet()
        outlet.name = name
        outlet.phone = phone
        outlet.address = address
        // Insert to database
        image?.let { image ->
            imageNameFromUri?.let { imageName ->
                repository.addOutlet(outlet, image, imageName, object : OutletDataSource.addOutletCallback {
                    override fun onSuccess(message: String) {
                        outletListener?.onSuccess(message)
                    }

                    override fun onFailure(error: String) {
                        outletListener?.onFailure(error)
                    }

                })
            }
        }
    }

}