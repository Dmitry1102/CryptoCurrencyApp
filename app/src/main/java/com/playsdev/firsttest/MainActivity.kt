package com.playsdev.firsttest

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.playsdev.firsttest.databinding.ActivityMainBinding
import com.playsdev.firsttest.service.InternetService
import com.playsdev.firsttest.service.InternetService.Companion.CHECK_ACTION
import com.playsdev.firsttest.service.NetworkChangeListener
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.android.inject

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val networkChangeListener: NetworkChangeListener by inject()
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var intentFilter: IntentFilter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layout = binding.collapsingToolbarLayout
        val toolbar = binding.toolbar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.screen_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        layout.setupWithNavController(toolbar,navController,appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)

        intentFilter = IntentFilter()
        intentFilter!!.addAction(CHECK_ACTION)
        startService(Intent(this, InternetService::class.java))
    }

    override fun onRestart() {
        super.onRestart()
        registerReceiver(networkChangeListener,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeListener)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkChangeListener,intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}