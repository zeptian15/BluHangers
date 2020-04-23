package com.zealous.bluhangers.ui.auth

import android.widget.EditText

interface AuthListener {
    fun onStarted()
    fun onSuccess(message: String)
    fun onFailure(message: String)
    fun onFinish()
}