package com.riyaz.foodrunner

import android.content.Intent
import android.media.tv.TvContract
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashScreenActivity : AppCompatActivity() {

    lateinit var logoImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        logoImageView = findViewById(R.id.logoInSplashScreen)

        logoImageView.alpha = 0f
        logoImageView.animate().alpha(1f).withEndAction {
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}