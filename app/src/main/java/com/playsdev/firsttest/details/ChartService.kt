package com.playsdev.firsttest.details

import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.playsdev.firsttest.R
import com.playsdev.firsttest.data.ChartData

object ChartService {

    fun initLineChart(score: LineChart) {
        val xAxis: XAxis = score.xAxis

        score.axisRight.isEnabled = false
        score.legend.isEnabled = false
        score.axisLeft.setDrawGridLines(false)
        score.description.isEnabled = false
        score.animateX(1000, Easing.EaseInSine)

        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

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
        lineDataSet.disableDashedLine()

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }


}
