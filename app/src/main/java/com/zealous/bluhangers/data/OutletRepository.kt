package com.zealous.bluhangers.data

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.zealous.bluhangers.data.model.Outlet
import com.zealous.bluhangers.data.source.OutletDataSource
import com.zealous.bluhangers.utils.Constants.NODE_OUTLETS

class OutletRepository : OutletDataSource {

    private val mDatabase = FirebaseDatabase.getInstance().getReference(NODE_OUTLETS)

    override fun addOutlet(
        outlet: Outlet,
        imagePath: Uri,
        fileName: String,
        callback: OutletDataSource.addOutletCallback
    ) {
        val mStorageRef = FirebaseStorage.getInstance().getReference(NODE_OUTLETS).child(fileName)
        mStorageRef.putFile(imagePath).apply {
            addOnSuccessListener {
                mStorageRef.downloadUrl.apply {
                    addOnCompleteListener {
                        val outletId = mDatabase.push().key
                        outlet.image = it.result.toString()
                        Log.d("ImageData", outlet.image)
                        mDatabase.child(outletId.toString()).setValue(outlet)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    callback.onSuccess("Berhasil memasukan ke database")
                                } else {
                                    callback.onFailure(it.exception?.message.toString())
                                }
                            }
                    }
                }
            }
            addOnFailureListener {
                callback.onFailure(it.message.toString())
            }
        }
    }

}