package com.playsdev.testapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.playsdev.firsttest.MainActivity
import com.playsdev.firsttest.coindatabase.Coin
import com.playsdev.firsttest.databinding.ActivitySplashBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.firsttest.viewmodel.CoinViewModel
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

//            lifecycleScope.launch {
//                viewModel.getCoin().distinctUntilChanged().collect {
//
//                }
//            }
//
//            lifecycle.coroutineScope.launchWhenResumed {
//                viewModel.list.collect { startList ->
//                    val list =  startList }
//            }


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, DURATION)
    }

    private fun addToDataBase(list: MutableList<Coin>) {
        coinViewModel.addToDataBase(list)
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