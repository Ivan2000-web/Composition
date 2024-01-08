package com.vansoft.composition.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.vansoft.composition.R
import com.vansoft.composition.presentation.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY = 3000L // Задержка в миллисекундах

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish() // Закрываем Splash Activity после перехода на основную активность
        }, SPLASH_DELAY)
    }
}