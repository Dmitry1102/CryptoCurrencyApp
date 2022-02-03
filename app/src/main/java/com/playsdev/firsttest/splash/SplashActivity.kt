package com.playsdev.testapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.playsdev.firsttest.MainActivity
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.databinding.ActivitySplashBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    private val viewModel by inject<CoinDataBaseViewModel>()

    private val coinAdapter = CoinAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            viewModel.getCoiList().onEach {
//                coinAdapter.submitList(it.map { coin->
//                    CoinResponce(
//                        current_price = coin.current_price,
//                        id = coin.id,
//                        image = coin.image,
//                        symbol = coin.symbol
//                    )
//                })
//            }.launchIn(lifecycleScope)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, DURATION)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val DURATION: Long = 1000
    }


}