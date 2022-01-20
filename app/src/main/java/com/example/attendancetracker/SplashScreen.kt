package com.example.attendancetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val text: TextView = findViewById(R.id.attendance)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_slide)
        val image: ImageView = findViewById(R.id.logo)
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_slide)
        image.startAnimation(topAnimation)
        text.startAnimation(bottomAnimation)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}