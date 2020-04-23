package com.zealous.bluhangers.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

import com.zealous.bluhangers.R

/**
 * A simple [Fragment] subclass.
 */
class LoadingDialog(activity: Activity) {

    private var activity: Activity? = null
    private var dialog: Dialog? = null

    init {
        this.activity = activity
    }


    fun showDialog(){
        dialog = Dialog(activity!!)
        dialog?.setCancelable(false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.loading_dialog)
        dialog?.show()
    }

    fun hideDialog(){
        dialog?.dismiss()
    }

}
