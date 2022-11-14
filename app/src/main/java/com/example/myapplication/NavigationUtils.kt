package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class NavigationUtils: AppCompatActivity() {
    companion object {
        @JvmStatic
        fun navigateTo(context : Context, activity: Activity):Boolean {
            var intent = Intent(context,activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return true
        }
        @JvmStatic
        fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager): Boolean {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container,fragment)
            fragmentTransaction.commit()
            return true
        }
    }

}