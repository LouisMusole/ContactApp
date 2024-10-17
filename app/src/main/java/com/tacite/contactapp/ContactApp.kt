package com.tacite.contactapp

import android.app.Application
import com.tacite.contactapp.di.localDBModule
import com.tacite.contactapp.di.repositoryModule
import com.tacite.contactapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContactApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ContactApp)
            modules(localDBModule, repositoryModule, viewModelModule)
        }
    }
}