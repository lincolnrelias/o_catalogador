package com.example.myapplication.collections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.example.myapplication.BottomNavigationUtils
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class MyCollectionsActivity : AppCompatActivity() {
    var currCollection: Pair<String?, Collection>? = null
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 1
    lateinit var storageRef: FirebaseStorage
    lateinit var databaseRef: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        databaseRef = FirebaseDatabase.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView, baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.my_collections)
        NavigationUtils.replaceFragment(CollectionsFragment(), supportFragmentManager)
    }
}