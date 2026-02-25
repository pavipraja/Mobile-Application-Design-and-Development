package com.example.scientificcalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentExpression = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // Number buttons
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2,
            R.id.btn3, R.id.but4, R.id.but5,
            R.id.but6, R.id.but7, R.id.but8, R.id.but9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                val value = (it as Button).text.toString()
                currentExpression += value
                tvResult.text = currentExpression
            }
        }

        // Operators
        findViewById<Button>(R.id.butplus).setOnClickListener { addOperator("+") }
        findViewById<Button>(R.id.butminus).setOnClickListener { addOperator("-") }
        findViewById<Button>(R.id.multiply).setOnClickListener { addOperator("*") }
        findViewById<Button>(R.id.btndiv).setOnClickListener { addOperator("/") }

        // Dot
        findViewById<Button>(R.id.btndot).setOnClickListener {
            currentExpression += "."
            tvResult.text = currentExpression
        }

        // Clear
        findViewById<Button>(R.id.btnc).setOnClickListener {
            currentExpression = ""
            tvResult.text = "0"
        }

        // Equal
        findViewById<Button>(R.id.butEqual).setOnClickListener {
            try {
                val result = evaluateExpression(currentExpression)
                currentExpression = formatResult(result)
                tvResult.text = currentExpression
            } catch (e: Exception) {
                tvResult.text = "Error"
                currentExpression = ""
            }
        }

        // Scientific buttons
        findViewById<Button>(R.id.btnsin).setOnClickListener { scientific { sin(Math.toRadians(it)) } }
        findViewById<Button>(R.id.btncos).setOnClickListener { scientific { cos(Math.toRadians(it)) } }
        findViewById<Button>(R.id.btntan).setOnClickListener { scientific { tan(Math.toRadians(it)) } }
        findViewById<Button>(R.id.btnlog).setOnClickListener { scientific { log10(it) } }
        findViewById<Button>(R.id.btnroot).setOnClickListener { scientific { sqrt(it) } }
    }

    private fun addOperator(op: String) {
        if (currentExpression.isNotEmpty() &&
            !currentExpression.last().toString().matches(Regex("[+\\-*/]"))
        ) {
            currentExpression += op
            tvResult.text = currentExpression
        }
    }

    @SuppressLint("SetTextI18n")
    private fun scientific(operation: (Double) -> Double) {
        try {
            val number = currentExpression.toDouble()
            val result = operation(number)
            currentExpression = formatResult(result)
            tvResult.text = currentExpression
        } catch (e: Exception) {
            tvResult.text = "Error"
            currentExpression = ""
        }
    }

    private fun evaluateExpression(expr: String): Double {
        val tokens = expr.split(Regex("(?<=[-+*/])|(?=[-+*/])"))
        var result = tokens[0].toDouble()

        var i = 1
        while (i < tokens.size) {
            val operator = tokens[i]
            val nextNumber = tokens[i + 1].toDouble()

            result = when (operator) {
                "+" -> result + nextNumber
                "-" -> result - nextNumber
                "*" -> result * nextNumber
                "/" -> result / nextNumber
                else -> result
            }
            i += 2
        }
        return result
    }

    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
}