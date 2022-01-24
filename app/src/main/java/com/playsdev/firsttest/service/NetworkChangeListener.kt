package com.playsdev.firsttest.service

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Button
import com.playsdev.firsttest.R

class NetworkChangeListener(
    private val checkInternet: CheckInternet
) : BroadcastReceiver() {

    @SuppressLint("InflateParams")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (!checkInternet.isOnline(context!!)) {
            val builder = AlertDialog.Builder(context)
            val layout =
                LayoutInflater.from(context).inflate(R.layout.service_fragment, null)
            builder.setView(layout)
            val retryButton = layout.findViewById<Button>(R.id.btn_again)
            val dialog = builder.create()
            dialog.show()
            dialog.setCancelable(false)

            retryButton.setOnClickListener {
                dialog.dismiss()
                onReceive(context, intent)
            }
        }
    }
}