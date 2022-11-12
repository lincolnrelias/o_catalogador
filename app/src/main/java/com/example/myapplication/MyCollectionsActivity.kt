package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyCollectionsActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView,baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.my_collections)

    }
}