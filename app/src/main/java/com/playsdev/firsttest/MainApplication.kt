package com.playsdev.firsttest

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.playsdev.firsttest.cloud.CoinService
import com.playsdev.firsttest.coindatabase.CoinDataBase
import com.playsdev.firsttest.coindatabase.CoinDatabaseConstructor
import com.playsdev.firsttest.details.ChartService
import com.playsdev.firsttest.persondatabase.PersonDataBase
import com.playsdev.firsttest.persondatabase.PersonDatabaseConstructor
import com.playsdev.firsttest.repository.CoinDataBaseRepository
import com.playsdev.firsttest.repository.CoinRepository
import com.playsdev.firsttest.repository.PersonRepository
import com.playsdev.firsttest.service.CheckInternet
import com.playsdev.firsttest.service.NetworkChangeListener
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import com.playsdev.firsttest.viewmodel.PersonDataViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module

@DelicateCoroutinesApi
@KoinApiExtension
class MainApplication: Application(), KoinComponent {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(repository,service,viewModels,cloudModule,internet,storageModule))
        }
    }

    private val repository = module {
        single { PersonRepository(get()) }
        single { CoinRepository(get()) }
        single{ CoinDataBaseRepository(get(),get(),get()) }
    }

    private val service = module {
        single { ChartService }
    }

    private val viewModels = module {
        viewModel { PersonDataViewModel(get()) }
        viewModel { CoinViewModel(get()) }
        viewModel { CoinDataBaseViewModel(get()) }
    }

    private val cloudModule = module {
        single { CoinService.apiService() }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val internet = module {
        single { CheckInternet() }
        single { NetworkChangeListener() }
    }

    private val storageModule = module {
        single { PersonDatabaseConstructor.create(get()) }
        factory { get<PersonDataBase>().personDao() }
        single { CoinDatabaseConstructor.create(get()) }
        factory { get<CoinDataBase>().coinDao() }
        factory { get<CoinDataBase>().coinKeyDao() }
    }
}