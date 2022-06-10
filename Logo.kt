package com.example.zakupy

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Logo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        val toptext = findViewById<TextView>(R.id.toptextView)
        val middletext = findViewById<TextView>(R.id.middletextView)
        val bottomtext = findViewById<TextView>(R.id.bottomtextView)

        toptext.startAnimation(topAnimation)
        middletext.startAnimation(middleAnimation)
        bottomtext.startAnimation(bottomAnimation)

        val thread = Thread {
            run {
                Thread.sleep(3000)
            }
            runOnUiThread {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        thread.start()
    }
}
