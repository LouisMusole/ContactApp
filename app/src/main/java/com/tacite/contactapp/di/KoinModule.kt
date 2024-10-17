package com.tacite.contactapp.di

import androidx.room.Room
import com.tacite.contactapp.data.local.ContactDao
import com.tacite.contactapp.data.local.ContactDatabase
import com.tacite.contactapp.data.repository.ContactRepository
import com.tacite.contactapp.ui.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val localDBModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ContactDatabase::class.java,
            "contact.db"
        ).fallbackToDestructiveMigration().build()
    }

    single<ContactDao> {
        val database = get<ContactDatabase>()
        database.contactDao()
    }
}

val repositoryModule = module {
    single { ContactRepository(get()) }
}


val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}
