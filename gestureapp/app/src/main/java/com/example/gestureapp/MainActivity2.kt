package com.example.gestureapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity2: AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector
    private lateinit var tv2: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        tv2 = findViewById(R.id.tv2)

        gestureDetector = GestureDetector(this,
            object : GestureDetector.SimpleOnGestureListener() {

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
                            tv2.text = "Swipe Right 👉"
                            Toast.makeText(this@MainActivity2, "Right Swipe", Toast.LENGTH_SHORT).show()
                        } else {
                            tv2.text = "Swipe Left 👈"
                            Toast.makeText(this@MainActivity2, "Left Swipe", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity2, MainActivity::class.java)
                            startActivity(intent)
                            return true
                        }
                    } else {
                        if (diffY > 0) {
                            tv2.text = "Swipe Down ⬇"
                            Toast.makeText(this@MainActivity2, "Down Swipe", Toast.LENGTH_SHORT).show()
                        } else {
                            tv2.text = "Swipe Up ⬆"
                            Toast.makeText(this@MainActivity2, "Up Swipe", Toast.LENGTH_SHORT).show()
                        }
                    }
                    return true
                }
            })

        tv2.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }
}