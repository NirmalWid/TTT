package com.example.qr_scanner

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Roles : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roles)

        val btScan = findViewById<Button>(R.id.button2)
        val btupt = findViewById<Button>(R.id.button3)
        val btTrain = findViewById<Button>(R.id.button4)

        val animator = ObjectAnimator.ofFloat(btTrain, "scaleY", 1f, 0.5f, 1f)
        animator.duration = 1000

        val animator1 = ObjectAnimator.ofFloat(btScan, "scaleY", 1f, 0.5f, 1f)
        animator1.duration = 1000

        //val animator2 = ObjectAnimator.ofFloat(btupt, "scaleY", 1f, 0.5f, 1f)
       // animator2.duration = 1000

        btScan.setOnClickListener {
            animator1.start()
            val intent = Intent(this, Checker::class.java)
            startActivity(intent)
        }

        btTrain.setOnClickListener {
            animator.start()
            val intent = Intent(this, TrainReg::class.java)
            startActivity(intent)

        }

        btupt.setOnClickListener {
            //animator2.start()
            val intent = Intent(this, TrainEdit::class.java)
            startActivity(intent)
        }

    }
}