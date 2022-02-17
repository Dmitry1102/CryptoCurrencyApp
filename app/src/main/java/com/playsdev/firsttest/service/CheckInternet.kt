package com.playsdev.firsttest.service

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.playsdev.firsttest.R

class CheckInternet {
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
       while (true){
           val connectivityManager =
               context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           val capabilities =
               connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
           if (capabilities != null) {
               when {
                   capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                       return true
                   }
                   capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                       return true
                   }
                   capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                       return true
                   }
               }
           }
           return false
       }

    }

}