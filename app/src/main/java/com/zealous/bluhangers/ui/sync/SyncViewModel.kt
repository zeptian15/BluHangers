package com.zealous.bluhangers.ui.sync

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zealous.bluhangers.data.SyncRepository
import com.zealous.bluhangers.data.model.Outlet
import com.zealous.bluhangers.data.model.User
import com.zealous.bluhangers.data.source.SyncDataSource
import com.zealous.bluhangers.ui.sync.outlet.SyncOutletListener
import com.zealous.bluhangers.utils.Constants.DEFAULT_OUTLET
import com.zealous.bluhangers.utils.Coroutines
import java.util.*
import kotlin.collections.ArrayList

class SyncViewModel(
    private val repository: SyncRepository
) : ViewModel() {

    private var _outlets = MutableLiveData<ArrayList<Outlet>>()
    val outlets: LiveData<ArrayList<Outlet>>
        get() = _outlets

    private var _outlet = MutableLiveData<Outlet>()
    val outlet: LiveData<Outlet>
        get() = _outlet

    var syncOutletListener: SyncOutletListener? = null
    var syncListener: SyncListener? = null

    fun fetchUser() {
        Coroutines.main {
            repository.loadUser(object: SyncDataSource.loadUserCallback {
                override fun onSuccess(user: User) {
                    user.id_outlet?.let { checkOutlet(it) }
                }

                override fun onFailure(error: String) {
                    syncListener?.onFailure(error)
                }

            })
        }
    }

    fun loadRealtime(){
        Coroutines.main {
            repository.loadRealtime(object : SyncDataSource.loadRealtimeCallback {
                override fun onAdded(outlet: Outlet) {
                    _outlet.value = outlet
                }

                override fun onUpdated(outlet: Outlet) {
                    _outlet.value = outlet
                }

                override fun onDeleted(outlet: Outlet) {
                    _outlet.value = outlet
                }

                override fun onFailure(error: String) {
                    syncOutletListener?.onError(error)
                }

            })
        }
    }

    fun toRegisterOutlet(){
        syncOutletListener?.toRegister()
    }

    fun back(){
        syncOutletListener?.onFinish()
    }

    private fun checkOutlet(userOutlet: String){
        if(userOutlet == DEFAULT_OUTLET){
            syncListener?.onOutletNotExist()
        } else {
            syncListener?.onOutletExist()
        }
    }

    fun toSyncOutlet(){
        syncListener?.toSyncOutlet()
    }
}