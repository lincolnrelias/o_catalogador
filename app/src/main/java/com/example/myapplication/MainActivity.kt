package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Objects

class MainActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(FeedFragment())
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView,baseContext)
    }



    private fun replaceFragment(fragment: Fragment): Boolean {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
        return true
    }
}