package com.playsdev.firsttest.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


@DelicateCoroutinesApi
class InternetService : Service() {
    private val checkInternet: CheckInternet by inject()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        GlobalScope.launch {
            checkConnection()
        }
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun checkConnection() {
        while (true) {
            delay(TIME_CHECK)
            val broadcastIntent = Intent()
            broadcastIntent.action = CHECK_ACTION
            broadcastIntent.putExtra(
                "internet_status",
                "${checkInternet.isOnline(this@InternetService)}"
            )
            sendBroadcast(broadcastIntent)
        }

    }

    companion object {
        const val CHECK_ACTION = "check_internet"
        private const val TIME_CHECK: Long = 13000
    }

}