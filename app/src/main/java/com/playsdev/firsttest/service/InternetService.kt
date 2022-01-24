package com.playsdev.firsttest.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent


class InternetService : Service(){
    private val checkInternet:CheckInternet by inject()
    private val networkChangeListener:NetworkChangeListener by inject()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!checkInternet.isOnline(applicationContext))
            Handler(Looper.myLooper()!!).postDelayed(runnable, TIME_CHECK)
        return START_STICKY
    }

    private val runnable = Runnable {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener,intentFilter)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val TIME_CHECK: Long = 13000
    }

}