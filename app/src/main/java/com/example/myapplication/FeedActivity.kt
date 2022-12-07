package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.NavigationUtils.Companion.replaceFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class FeedActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 0
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        replaceFragment(FeedFragment(),supportFragmentManager,false)
        navView = findViewById(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView,baseContext)
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.main_title)
        findViewById<ConstraintLayout>(R.id.main_coll_section).visibility = View.VISIBLE
    }

}