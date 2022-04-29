package com.sberkozd.bitcointicker.ui.detail

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.LineDataSet
import com.sberkozd.bitcointicker.R
import javax.inject.Inject

class LineStyle @Inject constructor(private val context: Context) {

    fun styleLineDataSet(lineDataSet: LineDataSet, colorInt: Int) =
        lineDataSet.apply {
            color = ContextCompat.getColor(context, android.R.color.darker_gray)
            valueTextColor = ContextCompat.getColor(context, R.color.white)
            setDrawValues(false)
            lineWidth = 3f
            isHighlightEnabled = true
            setDrawHighlightIndicators(false)
            setDrawCircles(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawFilled(true)
            fillDrawable = ContextCompat.getDrawable(context, R.drawable.bg_linechart)
                ?.apply { setTint(colorInt) }
        }
}