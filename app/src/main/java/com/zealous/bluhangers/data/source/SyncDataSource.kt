package com.zealous.bluhangers.data.source

import com.zealous.bluhangers.data.model.Outlet
import com.zealous.bluhangers.data.model.User

interface SyncDataSource {
    fun loadUser(callback: loadUserCallback)
    fun loadRealtime(callback: loadRealtimeCallback)

    interface loadUserCallback {
        fun onSuccess(user: User)
        fun onFailure(error: String)
    }

    interface loadRealtimeCallback {
        fun onAdded(outlet: Outlet)
        fun onUpdated(outlet: Outlet)
        fun onDeleted(outlet: Outlet)
        fun onFailure(error: String)
    }
}