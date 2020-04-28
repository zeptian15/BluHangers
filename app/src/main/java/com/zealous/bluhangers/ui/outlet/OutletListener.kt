package com.zealous.bluhangers.ui.outlet

interface OutletListener {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure(error: String)
    fun onError(error: String)
    fun onPickImage()
    fun onFinish()
}