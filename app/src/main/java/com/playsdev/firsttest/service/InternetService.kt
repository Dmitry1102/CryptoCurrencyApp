package com.playsdev.firsttest.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import org.koin.android.ext.android.inject

class InternetService : Service() {
    private val checkInternet = CheckInternet()
    private val networkChangeListener = NetworkChangeListener(checkInternet)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!checkInternet.isOnline(applicationContext))
            Handler(Looper.myLooper()!!).postDelayed(runnable, TIME_CHECK)
        return super.onStartCommand(intent, flags, startId)
    }

    private val runnable = Runnable {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener, intentFilter)
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    companion object {
        private const val TIME_CHECK: Long = 13000
    }

}