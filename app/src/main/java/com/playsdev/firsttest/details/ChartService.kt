package com.playsdev.firsttest.details

import android.annotation.SuppressLint
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.playsdev.firsttest.R
import com.playsdev.firsttest.data.ChartData

object ChartService {

    @SuppressLint("ResourceAsColor")
    fun initLineChart(score: LineChart) {
        val xAxis: XAxis = score.xAxis

        score.axisRight.isEnabled = false
        score.axisLeft.isEnabled = false
        score.legend.isEnabled = false
        score.axisLeft.setDrawGridLines(false)
        score.description.isEnabled = false
        score.animateX(1000)

        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.axisLineColor = R.color.orange

        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }

    fun setDataToLineChart(chartData: ChartData, lineChart: LineChart) {
        val entries: ArrayList<Entry> = ArrayList()
        for (i in chartData.prices.indices) {
            entries.add(Entry(i.toFloat(), chartData.prices[i][1].toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")
        lineDataSet.setColors(R.color.orange)
        lineDataSet.lineWidth = 3f
        lineDataSet.setDrawFilled(false)
        lineDataSet.setDrawCircles(false)
        lineDataSet.disableDashedLine()

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }


}
