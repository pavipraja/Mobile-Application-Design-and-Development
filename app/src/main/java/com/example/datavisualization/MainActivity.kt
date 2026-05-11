package com.example.datavisualization
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datavisualization.databinding.ActivityMainBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.Locale
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val labels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sun")
    // Default sample data for immediate chart display
    private val expenses = mutableListOf(120f, 180f, 90f, 250f, 300f, 140f,
        200f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()
        setupCharts()
        updateSummary()
        binding.btnUpdate.setOnClickListener {
            updateData()
        }
    }
    private fun setupSpinner() {
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, labels)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
        )
        binding.spinnerDays.adapter = adapter
    }
    private fun updateData() {
        val amountStr = binding.etAmount.text.toString().trim()
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount",
                Toast.LENGTH_SHORT).show()
            return
        }
        val amount = amountStr.toFloatOrNull()
        if (amount == null) {
            Toast.makeText(this, "Enter a valid amount",
                Toast.LENGTH_SHORT).show()
            return
        }
        if (amount < 0f) {
            Toast.makeText(this, "Amount cannot be negative",
                Toast.LENGTH_SHORT).show()
            return
        }
        val dayIndex = binding.spinnerDays.selectedItemPosition
        expenses[dayIndex] = amount
        updateCharts()
        updateSummary()
        binding.etAmount.text?.clear()
        Toast.makeText(this, "Dashboard Updated", Toast.LENGTH_SHORT).show()
    }
    private fun setupCharts() {
        val xAxisFormatter = IndexAxisValueFormatter(labels)
        // Bar Chart Setup
        binding.barChart.apply {
            description.isEnabled = false
            setFitBars(true)
            animateY(1000)
            legend.apply {
                isEnabled = true
                textSize = 12f
                form = Legend.LegendForm.SQUARE
            }
            xAxis.apply {
                valueFormatter = xAxisFormatter
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                textSize = 12f
            }
            axisRight.isEnabled = false
            axisLeft.axisMinimum = 0f
            axisLeft.textSize = 12f
        }
        // Line Chart Setup
        binding.lineChart.apply {
            description.isEnabled = false
            animateX(1000)
            legend.apply {
                isEnabled = true
                textSize = 12f
            }
            xAxis.apply {
                valueFormatter = xAxisFormatter
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                granularity = 1f
                textSize = 12f
            }
            axisRight.isEnabled = false
            axisLeft.axisMinimum = 0f
            axisLeft.textSize = 12f
        }
        // Pie Chart Setup
        binding.pieChart.apply {
            description.isEnabled = false
            setUsePercentValues(true)
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            animateY(1400)
            centerText = "Weekly Expense %"
            setCenterTextSize(18f)
            holeRadius = 45f
            transparentCircleRadius = 50f
            legend.apply {
                isEnabled = true
                textSize = 12f
            }
        }
        updateCharts()
    }
    private fun updateCharts() {
        updateBarChart()
        updateLineChart()
        updatePieChart()
    }
    private fun updateBarChart() {
        val barEntries = ArrayList<BarEntry>()
        for (i in expenses.indices) {
            barEntries.add(BarEntry(i.toFloat(), expenses[i]))
        }
        val barDataSet = BarDataSet(barEntries, "Daily Expense").apply {
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            valueTextSize = 11f
            valueTextColor = Color.BLACK
            setDrawValues(true)
        }
        val barData = BarData(barDataSet).apply {
            barWidth = 0.6f
            setValueFormatter(CurrencyValueFormatter())
        }
        binding.barChart.data = barData
        binding.barChart.invalidate()
    }
    private fun updateLineChart() {
        val lineEntries = ArrayList<Entry>()
        for (i in expenses.indices) {
            lineEntries.add(Entry(i.toFloat(), expenses[i]))
        }
        val lineDataSet = LineDataSet(lineEntries, "Weekly Expense Trend").apply {
                color = Color.BLUE
                setCircleColor(Color.BLUE)
                valueTextColor = Color.BLACK
                valueTextSize = 11f
                lineWidth = 3f
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawFilled(true)
                fillColor = Color.CYAN
                fillAlpha = 50
                setDrawValues(true)
        }
        val lineData = LineData(lineDataSet).apply {
            setValueFormatter(CurrencyValueFormatter())
        }
        binding.lineChart.data = lineData
        binding.lineChart.invalidate()
    }
private fun updatePieChart() {
    val pieEntries = ArrayList<PieEntry>()
    for (i in expenses.indices) {
        if (expenses[i] > 0f) {
            pieEntries.add(PieEntry(expenses[i], labels[i]))
        }
    }
    if (pieEntries.isEmpty()) {
        binding.pieChart.clear()
        binding.pieChart.centerText = "No Data Available"
        binding.pieChart.invalidate()
        return
    }
    val pieDataSet = PieDataSet(pieEntries, "Expense Share").apply {
        colors = ColorTemplate.JOYFUL_COLORS.toList()
        sliceSpace = 3f
        valueTextSize = 12f
        valueTextColor = Color.BLACK
    }
    val pieData = PieData(pieDataSet)
    binding.pieChart.data = pieData
    binding.pieChart.centerText = "Weekly Expense %"
    binding.pieChart.invalidate()
}
private fun updateSummary() {
    val total = expenses.sum()
    val maxExpense = expenses.maxOrNull() ?: 0f
    val highestDayIndex = expenses.indexOf(maxExpense)
    val highestDay = if (highestDayIndex >= 0) labels[highestDayIndex]
    else "-"
    binding.tvSummary.text = "Total Expense: ${formatCurrency(total)}"
    binding.tvHighest.text = "Highest Spending Day: $highestDay (${formatCurrency(maxExpense)})"
}
private fun formatCurrency(value: Float): String {
    return String.format(Locale.US, "$%.2f", value)
}
private class CurrencyValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return String.format(Locale.US, "$%.0f", value)
    }
}
}
