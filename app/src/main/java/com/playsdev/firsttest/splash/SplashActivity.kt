package com.playsdev.testapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.playsdev.firsttest.MainActivity
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.ActivitySplashBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@DelicateCoroutinesApi
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: CoinViewModel by viewModel()
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    private var listCoin: List<Coin>? = null
    private val coinViewModel by inject<CoinDataBaseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(applicationContext, MainActivity::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                listCoin = viewModel.getCoin()
                delay(DURATION)
                addToDataBase(listCoin!!)
                startActivity(intent)
            }
        }
    }

    private fun addToDataBase(data: List<Coin>) {
        coinViewModel.addToDataBase(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val DURATION: Long = 3000
    }


}