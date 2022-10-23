package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(FeedFragment())
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        navView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.feed_menu_item->{
                    replaceFragment(FeedFragment())
                }
                R.id.collections_menu_item->{
                    replaceFragment(CollectionsFragment())
                }
                R.id.add_collection_menu_item->{
                    replaceFragment(AddCollectionFragment())
                }
                R.id.search_menu_item->{
                    replaceFragment(SearchFragment())
                }
                R.id.profile_menu_item->{
                    replaceFragment(ProfileFragment())
                }
                //shouldn't get here really
                else -> {false}
            }
        }
    }
    private fun replaceFragment(fragment: Fragment): Boolean {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
        return true
    }
}