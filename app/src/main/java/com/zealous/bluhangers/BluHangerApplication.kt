package com.zealous.bluhangers

import android.app.Application
import com.zealous.bluhangers.data.OutletRepository
import com.zealous.bluhangers.data.SyncRepository
import com.zealous.bluhangers.data.UserRepository
import com.zealous.bluhangers.ui.auth.AuthViewModelFactory
import com.zealous.bluhangers.ui.outlet.OutletViewModelFactory
import com.zealous.bluhangers.ui.sync.SyncViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BluHangerApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BluHangerApplication))

        bind() from singleton { UserRepository() }
        bind() from singleton { OutletRepository() }
        bind() from singleton { SyncRepository() }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { OutletViewModelFactory(instance()) }
        bind() from provider { SyncViewModelFactory(instance()) }
    }
}