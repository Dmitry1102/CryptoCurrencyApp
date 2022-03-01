package com.playsdev.firsttest.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import coil.load
import android.graphics.Color
import com.playsdev.firsttest.BaseFragment
import com.playsdev.firsttest.data.Coin
import com.playsdev.firsttest.databinding.DetailsFragmentBinding
import com.playsdev.firsttest.viewmodel.CoinViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.util.*

class DetailsFragment : BaseFragment<DetailsFragmentBinding>() {

    private var coinArgs: Coin? = null
    private val viewModel: CoinViewModel by viewModel()
    private val calendar = Calendar.getInstance()
    private val chartService by inject<ChartService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        coinArgs = DetailsFragmentArgs.fromBundle(args).modelArgs
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup
    ): DetailsFragmentBinding = DetailsFragmentBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        binding.tvName.text = coinArgs?.symbol
        setAdditionalInfo()
        if (coinArgs?.current_price!! >= 1) {
            binding.tvCurrency.text = coinArgs?.current_price.toString().plus(" $")
        } else {
            val decimal =
                coinArgs?.current_price?.toBigDecimal()?.setScale(5, RoundingMode.HALF_EVEN)
            binding.tvCurrency.text = decimal.toString().plus(" $")
        }
        binding.ivIcon.load(coinArgs?.image)
        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.buttonDay.setOnClickListener {
            pressedButton(binding.buttonDay)
            dayChart()
        }

        binding.buttonWeek.setOnClickListener {
            pressedButton(binding.buttonWeek)
            weekChart()
        }

        binding.buttonMonth.setOnClickListener {
            pressedButton(binding.buttonMonth)
            monthChart()
        }

        binding.buttonYear.setOnClickListener {
            pressedButton(binding.buttonYear)
            yearChart()
        }

        binding.buttonFullPeriod.setOnClickListener {
            pressedButton(binding.buttonFullPeriod)
            allTimeChart()
        }
    }

    private fun dayChart() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "1", "")
                viewModel.getChartData().collect {
                    binding.lineChart.let { it1 -> chartService.initLineChart(it1) }
                    binding.lineChart.let { it1 ->
                        chartService.setDataToLineChart(it, it1)
                    }
                }
            }
        }
    }

    private fun weekChart() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "7", "")
                viewModel.getChartData().collect {
                    binding.lineChart.let { it1 -> chartService.initLineChart(it1) }
                    binding.lineChart.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }
            }
        }
    }

    private fun monthChart() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "${calendar.get(Calendar.MONTH)}", "")
                viewModel.getChartData().collect {
                    binding.lineChart.let { it1 -> chartService.initLineChart(it1) }
                    binding.lineChart.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }

            }
        }
    }

    private fun yearChart() {
        lifecycleScope.launch {
            progressBar()
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.setChartData(coinArgs!!.id, "${calendar.get(Calendar.YEAR)}", "")
                viewModel.getChartData().collect {
                    binding.lineChart.let { it1 -> chartService.initLineChart(it1) }
                    binding.lineChart.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }
            }
        }
    }

    private fun allTimeChart() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                progressBar()
                viewModel.setChartData(coinArgs!!.id, "max", "")
                viewModel.getChartData().collect {
                    binding.lineChart.let { it1 -> chartService.initLineChart(it1) }
                    binding.lineChart.let { it1 -> chartService.setDataToLineChart(it, it1) }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor", "Range")
    private fun setAdditionalInfo() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getAdditionalInfo()
                viewModel.setAdditionalData().collect {
                    it.forEach { item ->
                        if (coinArgs?.id == item.id) {
                            if (item.price_change_percentage_24h < 0) {
                                binding.tvCurrencyPercent.setTextColor(Color.parseColor("#E81313"))
                            }
                            binding.tvCurrencyPercent.text =
                                item.price_change_percentage_24h.toString().plus(" $")
                            binding.tvCapCost.text =
                                item.market_cap.toString().dropLast(6).plus(" B")
                            binding.tvMin.text =
                                item.low_24h.toString().plus(" $")
                            binding.tvMax.text =
                                item.high_24h.toString().plus(" $")
                        }
                    }
                }
            }
        }
    }

    private fun pressedButton(button: Button) {
        button.isSelected = !button.isSelected
    }

    private suspend fun progressBar() {
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        var i = progressBar.progress
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

}
