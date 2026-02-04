package com.example.check_inout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        var tv=findViewById<TextView>(R.id.tv)
        val checkin=findViewById<Button>(R.id.`in`)
        val checkout=findViewById<Button>(R.id.out)
        var count=0

        checkin.setOnClickListener {
            count=count+1
            tv.text=count.toString()
        }
        checkout.setOnClickListener {
            count=count-1
            tv.text=count.toString()
        }

    }
}