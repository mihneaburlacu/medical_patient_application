package com.example.new_medical_application.presentation.physiologicaldata

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.example.new_medical_application.R
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(context: Context) : MarkerView(context, R.layout.marker_view_value_points) {
    private val textView: TextView = findViewById(R.id.tv_marker)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            textView.text = e.y.toString()
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}
