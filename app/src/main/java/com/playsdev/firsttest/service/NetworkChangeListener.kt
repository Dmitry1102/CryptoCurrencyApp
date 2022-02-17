package com.playsdev.firsttest.service

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.annotation.RequiresApi
import com.playsdev.firsttest.R
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.experimental.property.inject

@DelicateCoroutinesApi
class NetworkChangeListener() : BroadcastReceiver() {

    private val checkInternet = CheckInternet()

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == InternetService.CHECK_ACTION) {
            val builder = AlertDialog.Builder(context)
            val layout =
                LayoutInflater.from(context).inflate(R.layout.service_fragment, null)
            builder.setView(layout)
            val retryButton = layout.findViewById<Button>(R.id.btn_again)
            val dialog = builder.create()
            dialog.setCancelable(checkInternet.isOnline(context))

            if (intent.getStringExtra("internet_status") == "false") {
                dialog.show()
                if (!checkInternet.isOnline(context)) {
                    retryButton.setOnClickListener {
                        dialog.dismiss()
                        onReceive(context,intent)
                    }
                }else{
                    retryButton.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }
        }
    }
}