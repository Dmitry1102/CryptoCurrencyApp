package com.playsdev.firsttest.details

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.playsdev.firsttest.R
import com.playsdev.firsttest.data.ChartData

class LineChartService {

    companion object{

        fun setLineChart(data: ChartData): LineData {
            val entries = ArrayList<Entry>()
            entries.add(Entry(1f, data.price_change_24h.toFloat()))
            entries.add(Entry(2f, data.price_change_24h.toFloat()))
            entries.add(Entry(3f, data.price_change_24h.toFloat()))
            entries.add(Entry(4f, data.price_change_24h.toFloat()))
            entries.add(Entry(5f, data.price_change_24h.toFloat()))
            entries.add(Entry(6f, data.price_change_24h.toFloat()))

            val lineDataSet = LineDataSet(entries, "first")
            lineDataSet.color = R.color.green

            return LineData(lineDataSet)
        }
    }




}