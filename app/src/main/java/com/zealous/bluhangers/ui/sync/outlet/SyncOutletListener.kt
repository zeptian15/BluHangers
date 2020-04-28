package com.zealous.bluhangers.ui.sync.outlet

interface SyncOutletListener {
    fun onError(error: String)
    fun onFinish()
    fun toRegister()
}