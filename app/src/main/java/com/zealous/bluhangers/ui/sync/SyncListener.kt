package com.zealous.bluhangers.ui.sync

interface SyncListener {
    fun onSuccess(message: String)
    fun onFailure(error: String)
    fun onOutletExist()
    fun onOutletNotExist()
    fun toSyncOutlet()
}