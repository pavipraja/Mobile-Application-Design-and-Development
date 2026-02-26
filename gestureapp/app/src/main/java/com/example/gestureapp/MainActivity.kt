package com.example.gestureapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv = findViewById<TextView>(R.id.tv)

        gestureDetector = GestureDetector(this,
            object : GestureDetector.SimpleOnGestureListener() {

                override fun onLongPress(e: MotionEvent) {
                    tv.text = "Long Press"
                    Toast.makeText(this@MainActivity, "Long Press", Toast.LENGTH_SHORT).show()
                }

                override fun onDoubleTap(e: MotionEvent): Boolean {
                    tv.text = "Double Tap"
                    Toast.makeText(this@MainActivity, "Double Tap", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                    startActivity(intent)
                    return true
                }

                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {

                    val diffX = e2.x - e1!!.x
                    val diffY = e2.y - e1.y

                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (diffX > 0) {
                            tv.text = "Swipe Right 👉"
                            Toast.makeText(this@MainActivity, "Right Swipe", Toast.LENGTH_SHORT).show()
                        } else {
                            tv.text = "Swipe Left 👈"
                            Toast.makeText(this@MainActivity, "Left Swipe", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        if (diffY > 0) {
                            tv.text = "Swipe Down ⬇"
                            Toast.makeText(this@MainActivity, "Down Swipe", Toast.LENGTH_SHORT).show()
                        } else {
                            tv.text = "Swipe Up ⬆"
                            Toast.makeText(this@MainActivity, "Up Swipe", Toast.LENGTH_SHORT).show()
                        }
                    }
                    return true
                }
            })
        tv.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }
}