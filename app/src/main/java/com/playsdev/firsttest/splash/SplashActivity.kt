package com.playsdev.testapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.playsdev.firsttest.MainActivity
import com.playsdev.firsttest.adapter.CoinAdapter
import com.playsdev.firsttest.cloud.CoinResponce
import com.playsdev.firsttest.databinding.ActivitySplashBinding
import com.playsdev.firsttest.viewmodel.CoinDataBaseViewModel
import com.playsdev.testapp.main.MainFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    private val viewModel by inject<CoinDataBaseViewModel>()
    private var startList: MutableList<CoinResponce>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        lifecycleScope.launch {
            val list = viewModel.getCoinList().map {
                it.map { coin ->
                    CoinResponce(
                        current_price = coin.current_price ,
                        id = coin.id ,
                        image = coin.image,
                        symbol = coin.symbol
                    )
                }
            }

            list.collect {
                startList = it.toMutableList()
            }

        }

        Log.d("AVV", "$startList")


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            val transferIntent = Intent(this, MainFragment::class.java)
           //transferIntent.putExtra(LIST_TAG, startList)
            startActivity(intent)
        }, DURATION)
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