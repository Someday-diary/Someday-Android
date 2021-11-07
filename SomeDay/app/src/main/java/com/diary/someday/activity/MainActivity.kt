package com.diary.someday.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diary.someday.application.Application
import com.diary.someday.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }

    override fun onDestroy() {
        super.onDestroy()
        Application.lockNumber.delete()
    }
}