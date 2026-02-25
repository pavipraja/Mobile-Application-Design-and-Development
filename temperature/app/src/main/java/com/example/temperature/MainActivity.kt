package com.example.temperature

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editview=findViewById<EditText>(R.id.etTemp)
        val radioC=findViewById<RadioButton>(R.id.rbC)
        val radioF=findViewById<RadioButton>(R.id.rbF)
        val but=findViewById<Button>(R.id.btnConvert)
        val result=findViewById<TextView>(R.id.tvResult)

        but.setOnClickListener {
            val res=editview.text.toString()
            if(res.isEmpty()){
                result.text="Please enter the value"
                return@setOnClickListener
           }
            if(!radioC.isChecked && !radioF.isChecked)
            {
                result.text="Please select the entered format"
                return@setOnClickListener
            }
            var num=res.toDouble()
            if(radioC.isChecked){
                num=(num*9/5) + 32
                result.text="Fahrenheit: $num"
            }
            else{
                num=(num-32)*5/9
                result.text="Celsius: $num"
            }
        }
    }
}