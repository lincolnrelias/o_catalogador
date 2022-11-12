package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 3
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView,baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.visibility = View.GONE
        findViewById<LinearLayoutCompat>(R.id.search_bar)?.visibility = View.VISIBLE
    }
}