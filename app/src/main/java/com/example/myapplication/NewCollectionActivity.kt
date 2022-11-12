package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewCollectionActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 2
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_collection)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView,baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.new_collection)
        findViewById<AppCompatImageView>(R.id.header_config_btn).visibility = View.GONE

    }
}