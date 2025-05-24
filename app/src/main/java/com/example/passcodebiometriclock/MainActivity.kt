package com.example.passcodebiometriclock

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextView(this).apply {
            text = "Welcome to the App!"
            gravity = Gravity.CENTER
            textSize = 24f
        })
    }
}
