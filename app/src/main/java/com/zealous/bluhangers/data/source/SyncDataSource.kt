package com.zealous.bluhangers.data.source

import com.zealous.bluhangers.data.model.Outlet
import com.zealous.bluhangers.data.model.User

interface SyncDataSource {
    fun loadUser(callback: loadUserCallback)
    fun loadRealtime(callback: loadRealtimeCallback)
    fun loadOutlet(callback: loadOutletCallback)

    interface loadUserCallback {
        fun onSuccess(user: User)
        fun onFailure(error: String)
    }

    interface loadOutletCallback {
        fun onSuccess(outlets: ArrayList<Outlet>)
        fun onFailure(error: String)
    }

    interface loadRealtimeCallback {
        fun onAdded(outlet: Outlet)
        fun onUpdated(outlet: Outlet)
        fun onDeleted(outlet: Outlet)
        fun onFailure(error: String)
    }
}