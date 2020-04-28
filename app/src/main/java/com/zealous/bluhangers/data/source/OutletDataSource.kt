package com.zealous.bluhangers.data.source

import android.net.Uri
import com.zealous.bluhangers.data.model.Outlet

interface OutletDataSource {

    fun addOutlet(outlet: Outlet, imagePath: Uri, fileName: String, callback: addOutletCallback)

    interface addOutletCallback {
        fun onSuccess(message: String)
        fun onFailure(error: String)
    }
}