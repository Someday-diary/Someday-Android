package com.yademos.someday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_memu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val result = false

        when(item.itemId) {
            android.R.id.home -> {
                val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
                val gravity = GravityCompat.START // 현재 사이드 메뉴의 위치

                if (drawerLayout.isDrawerOpen(gravity)){ // 현재 지금 사이드 메뉴가 열려있는가?
                    drawerLayout.closeDrawer(gravity)
                } else {
                    drawerLayout.openDrawer(gravity)
                }
            }
        }
        return result
    }
}