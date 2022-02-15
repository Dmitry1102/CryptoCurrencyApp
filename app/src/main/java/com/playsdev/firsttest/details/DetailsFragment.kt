package com.playsdev.firsttest.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import coil.load
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.playsdev.firsttest.R
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.DetailsFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

class DetailsFragment: Fragment() {

    private var binding: DetailsFragmentBinding? = null
    private var coinArgs: Coin? = null
    private val viewModel by inject<CoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        coinArgs = DetailsFragmentArgs.fromBundle(args).modelArgs

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.tvName?.text = coinArgs?.symbol
        binding?.tvCurrency?.text = coinArgs?.current_price.toString()
        binding?.ivIcon?.load(coinArgs?.image)

        binding?.ivBack?.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding?.tvDay?.setOnClickListener {
            dayChart()
        }

    }

    private fun dayChart() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.indexCap("24H").collect {
//                    binding?.lineChart?.data = LineChartService.setLineChart(it)
//                }
            }
        }


    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}

//    val entries = ArrayList<Entry>()
//    val change = chartData.price_change_24h.toFloat()
//    val time = 3600000f
//    entries.add(Entry(change,time))
//    val dataSet = LineDataSet(entries,"LABEL")
//
//    dataSet.setDrawValues(false)
//    dataSet.setDrawFilled(true)
//    dataSet.lineWidth = 3f
//    dataSet.fillColor = R.color.gray
//    dataSet.fillAlpha = R.color.red
//
////Part5
//    binding?.lineChart?.xAxis?.labelRotationAngle = 0f
//
////Part6
//    binding?.lineChart?.data = LineData(dataSet)
//
////Part7
//    binding?.lineChart?.axisRight?.isEnabled = false
//    binding?.lineChart?.xAxis?.axisMaximum = 0.1f
//    binding?.lineChart?.setNoDataText("Not Found")
//
//    binding?.lineChart?.animateX(1800, Easing.EaseInExpo)
//
//
//}
