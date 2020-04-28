package com.zealous.bluhangers.ui.outlet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zealous.bluhangers.data.OutletRepository
import com.zealous.bluhangers.data.UserRepository

@Suppress("UNCHECKED_CAST")
class OutletViewModelFactory(
    private val repository: OutletRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OutletViewModel(repository) as T
    }
}