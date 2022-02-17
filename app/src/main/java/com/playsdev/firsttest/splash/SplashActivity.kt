package com.playsdev.testapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.playsdev.firsttest.MainActivity
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.ActivitySplashBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel by inject<CoinViewModel>()
    private var binding: ActivitySplashBinding? = null
    private var listCoin: List<Coin>? = null
    private val coinViewModel by inject<CoinDataBaseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch{
                listCoin = viewModel.getCoin()
                addToDataBase(listCoin!!)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, DURATION)
    }

    private fun addToDataBase(data: List<Coin>) {
        coinViewModel.addToDataBase(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    companion object {
        private const val DURATION: Long = 1000
    }


}