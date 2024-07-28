package com.example

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashTheScreen()
    }

    private fun splashTheScreen(){
        val quoteSplash:ImageView=findViewById(R.id.quote)
        quoteSplash.alpha=0f

        quoteSplash.animate().apply {
            alpha(0f)
            duration=800
        }.withEndAction{
            val intent=Intent(this@SplashScreenActivity,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }.start()
    }

}