package com.zealous.bluhangers.ui.outlet

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel

class OutletViewModel : ViewModel() {
    var name: String? = null
    var address: String? = null
    var phone: String? = null

    fun addOutlet(view: View){
        Toast.makeText(view.context, "Hai", Toast.LENGTH_LONG).show()
    }
}