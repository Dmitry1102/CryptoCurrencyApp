package com.playsdev.firsttest

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.playsdev.firsttest.databinding.ActivityMainBinding
import com.playsdev.firsttest.service.InternetService

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layout = binding?.collapsingToolbarLayout
        val toolbar = binding?.toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.screen_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        layout?.setupWithNavController(toolbar!!,navController,appBarConfiguration)
        binding?.bottomNav?.setupWithNavController(navController)


        startService(Intent(this,InternetService::class.java))

    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}