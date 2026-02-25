package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCalculate.setOnClickListener {

            val weight = etWeight.text.toString().toDoubleOrNull()
            val heightCm = etHeight.text.toString().toDoubleOrNull()

            if (weight == null || heightCm == null) {
                tvResult.text = "Enter valid values"
                return@setOnClickListener
            }

            val heightM = heightCm / 100
            val bmi = weight / heightM.pow(2)

            val category = when {
                bmi < 18.5 -> "Underweight"
                bmi < 25 -> "Normal"
                bmi < 30 -> "Overweight"
                else -> "Obese"
            }

            tvResult.text = "BMI: %.2f\nCategory: %s".format(bmi, category)
        }
    }
}