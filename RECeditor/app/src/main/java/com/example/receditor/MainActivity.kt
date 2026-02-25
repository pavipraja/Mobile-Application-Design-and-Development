package com.example.receditor

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main = findViewById<android.view.View>(R.id.main)
        val tv = findViewById<TextView>(R.id.tv)

        val but1 = findViewById<Button>(R.id.fc)
        val but2 = findViewById<Button>(R.id.bc)
        val but3 = findViewById<Button>(R.id.fs)

        // Font size
        var size = 35f
        but3.setOnClickListener {
            tv.textSize = size
            size += 4f
        }

        // Colors
        val color = listOf(
            Color.BLUE,
            Color.MAGENTA,
            Color.CYAN,
            Color.RED,
            Color.GREEN
        )

        // Text color
        var index = 0
        but1.setOnClickListener {
            tv.setTextColor(color[index])
            index = (index + 1) % color.size
        }

        // Background color (whole screen)
        var ind = 3
        but2.setOnClickListener {
            main.setBackgroundColor(color[ind])
            ind = (ind + 1) % color.size
        }
    }
}
