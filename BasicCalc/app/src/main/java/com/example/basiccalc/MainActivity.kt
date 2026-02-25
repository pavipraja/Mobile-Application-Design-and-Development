package com.example.basiccalc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val value1 = findViewById<EditText>(R.id.etValue1)
        val value2 = findViewById<EditText>(R.id.etValue2)
        val result = findViewById<EditText>(R.id.etResult)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSub = findViewById<Button>(R.id.btnSub)
        val btnMul = findViewById<Button>(R.id.btnMul)
        val btnDiv = findViewById<Button>(R.id.btnDiv)
        val btnMod = findViewById<Button>(R.id.btnMod)

        fun getValues(): Pair<Double, Double> {
            val num1 = value1.text.toString().toDoubleOrNull() ?: 0.0
            val num2 = value2.text.toString().toDoubleOrNull() ?: 0.0
            return Pair(num1, num2)
        }

        btnAdd.setOnClickListener {
            val (a, b) = getValues()
            result.setText((a + b).toString())
        }

        btnSub.setOnClickListener {
            val (a, b) = getValues()
            result.setText((a - b).toString())
        }

        btnMul.setOnClickListener {
            val (a, b) = getValues()
            result.setText((a * b).toString())
        }

        btnDiv.setOnClickListener {
            val (a, b) = getValues()
            if (b != 0.0)
                result.setText((a / b).toString())
            else
                result.setText("Cannot divide by 0")
        }

        btnMod.setOnClickListener {
            val (a, b) = getValues()
            result.setText((a % b).toString())
        }
    }
}