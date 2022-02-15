package com.playsdev.firsttest.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.playsdev.firsttest.MainActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent



@DelicateCoroutinesApi
class InternetService : Service(){
    private val checkInternet:CheckInternet by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        GlobalScope.launch {
            checkConnection()
            Log.d("AAA","${checkConnection()}")
        }
        return START_STICKY

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun checkConnection() {
        val broadcastIntent = Intent()
        broadcastIntent.action = CHECK_ACTION
        broadcastIntent.putExtra("internet_status", "${checkInternet.isOnline(this@InternetService)}")
        delay(TIME_CHECK)
        sendBroadcast(broadcastIntent)
    }

    companion object {
        const val CHECK_ACTION = "check_internet"
        private const val TIME_CHECK: Long = 13000
    }

}