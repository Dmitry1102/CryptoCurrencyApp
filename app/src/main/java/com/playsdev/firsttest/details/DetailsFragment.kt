package com.playsdev.firsttest.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import coil.load
import com.playsdev.firsttest.R
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.DetailsFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.math.RoundingMode
import java.util.*

class DetailsFragment : Fragment() {

    private var binding: DetailsFragmentBinding? = null

    private var coinArgs: Coin? = null
    private val viewModel by inject<CoinViewModel>()
    private val calendar = Calendar.getInstance()
    private var i = 0

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

        setAdditionalInfo()


        if (coinArgs?.current_price!! >= 1) {
            binding?.tvCurrency?.text = coinArgs?.current_price.toString().plus(" $")
        } else {
            val decimal =
                coinArgs?.current_price?.toBigDecimal()?.setScale(5, RoundingMode.HALF_EVEN)
            binding?.tvCurrency?.text = decimal.toString().plus(" $")
        }
        binding?.ivIcon?.load(coinArgs?.image)


        binding?.ivBack?.setOnClickListener {
            it.findNavController().popBackStack()
        }


        binding?.tvDay?.setOnClickListener {
            dayChart()

        }

        binding?.tvWeek?.setOnClickListener {
            weekChart()
        }

        binding?.tvMonth?.setOnClickListener {
            monthChart()
        }

        binding?.tvYear?.setOnClickListener {
            yearChart()
        }

        binding?.tvFullPeriod?.setOnClickListener {
            allTimeChart()
        }
    }


    private fun dayChart() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "1", "")
                viewModel.getChartData().collect {
                    val chartService = ChartService
                    binding?.lineChart?.let { it1 -> chartService.initLineChart(it1) }
                    binding?.lineChart?.let { it1 ->
                        chartService.setDataToLineChart(it, it1)

                    }
                }
            }
        }
    }

    private fun weekChart() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "7", "")
                viewModel.getChartData().collect {
                    val chartService = ChartService
                    binding?.lineChart?.let { it1 -> chartService.initLineChart(it1) }
                    binding?.lineChart?.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }
            }
        }
    }

    private fun monthChart() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "${calendar.get(Calendar.MONTH)}", "")
                viewModel.getChartData().collect {
                    val chartService = ChartService
                    binding?.lineChart?.let { it1 -> chartService.initLineChart(it1) }
                    binding?.lineChart?.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }

            }
        }
    }

    private fun yearChart() {
        lifecycleScope.launch {
            progressBar()
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.setChartData(coinArgs!!.id, "${calendar.get(Calendar.YEAR)}", "")
                viewModel.getChartData().collect {
                    val chartService = ChartService
                    binding?.lineChart?.let { it1 -> chartService.initLineChart(it1) }
                    binding?.lineChart?.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }
            }
        }
    }

    private fun allTimeChart() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "max", "")
                viewModel.getChartData().collect {
                    val chartService = ChartService
                    binding?.lineChart?.let { it1 -> chartService.initLineChart(it1) }
                    binding?.lineChart?.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }
            }
        }
    }

    private fun setAdditionalInfo() {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAdditionalInfo()
                viewModel.setAdditionalData().collect {
                    it.forEach { item ->
                        if (coinArgs?.id == item.id) {
                            binding?.tvCurrencyPercent?.text =
                                item.price_change_percentage_24h.toString().plus(" $")
                            binding?.tvCapCost?.text =
                                item.market_cap.toString().plus(" B")
                            binding?.tvMin?.text =
                                item.low_24h.toString().plus(" $")
                            binding?.tvMax?.text =
                                item.high_24h.toString().plus(" $")
                        }
                    }
                }
            }
        }
    }


    private suspend fun progressBar() {
        val progressBar = binding?.progressBar
        progressBar?.visibility = View.VISIBLE

        i = progressBar?.progress!!
        while (i < 100) {
            i += 1
            progressBar.progress = i
            try {
                delay(70)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        progressBar.visibility = View.INVISIBLE
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
