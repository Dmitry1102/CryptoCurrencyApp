package com.playsdev.firsttest.service

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import com.playsdev.firsttest.R
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class NetworkChangeListener() : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == InternetService.CHECK_ACTION) {
            val builder = AlertDialog.Builder(context)
            val layout =
                LayoutInflater.from(context).inflate(R.layout.service_fragment, null)
            builder.setView(layout)
            val retryButton = layout.findViewById<Button>(R.id.btn_again)
            val dialog = builder.create()
            if (intent.getStringExtra("internet_status") == "false") {
                dialog.setCancelable(false)
                dialog.show()

                retryButton.setOnClickListener {
                    dialog.dismiss()
                    onReceive(context, intent)
                }
            } else {
                dialog.cancel()
            }
        }
    }


}
