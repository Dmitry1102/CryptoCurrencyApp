package com.playsdev.testapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.playsdev.firsttest.MainActivity
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.databinding.ActivitySplashBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
import com.playsdev.testapp.main.MainFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")

class SplashActivity : AppCompatActivity() {

    private val viewModel by inject<CoinViewModel>()
    private var binding: ActivitySplashBinding? = null
    private val coinViewModel by inject<CoinDataBaseViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        Handler(Looper.getMainLooper()).postDelayed({
            lifecycle.coroutineScope.launchWhenResumed {
                viewModel.stateFlow.collect { startList ->
                    val list =  startList.toMutableList() }
            }


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, DURATION)
    }

    private fun addToDataBase(list: MutableList<CoinResponce>) {
        val dataList: List<Coin> = list.toList().map {
            Coin(
                current_price = it.current_price,
                id = it.id,
                image = it.image,
                symbol = it.symbol
            )
        }
        coinViewModel.addToDataBase(dataList)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    companion object {
        private const val DURATION: Long = 1000
        const val LIST_TAG = "LIST_TAG"
    }


}