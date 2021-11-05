package com.diary.someday.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.diary.someday.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startLoading()
    }

    private fun startLoading() {
        Handler().postDelayed(
            Runnable
            {
                finish()
            }, 2000
        )
    }
}