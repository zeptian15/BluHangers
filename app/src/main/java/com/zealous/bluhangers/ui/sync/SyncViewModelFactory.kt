package com.zealous.bluhangers.ui.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.data.SyncRepository

@Suppress("UNCHECKED_CAST")
class SyncViewModelFactory(
    private val repository: SyncRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SyncViewModel(repository) as T
    }
}