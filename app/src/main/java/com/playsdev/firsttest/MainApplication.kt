package com.playsdev.firsttest

import android.app.Application
import com.playsdev.firsttest.persondatabase.DatabaseConstructor
import com.playsdev.firsttest.persondatabase.PersonDataBase
import com.playsdev.firsttest.repository.PersonRepository
import com.playsdev.firsttest.service.CheckInternet
import com.playsdev.firsttest.service.NetworkChangeListener
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

@KoinApiExtension
class MainApplication: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(repository,viewModels,internet,storageModule))
        }
    }

    private val repository = module {
        factory { PersonRepository(get()) }
    }

    private val viewModels = module {
        viewModel { PersonDataViewModel(get()) }
    }

    private val internet = module {
        single { CheckInternet() }
        single { NetworkChangeListener() }
    }

    private val storageModule = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<PersonDataBase>().personDao() }
    }





}