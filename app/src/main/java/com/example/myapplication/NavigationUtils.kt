package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
        fun replaceFragment(fragment: Fragment, fragmentManager: FragmentManager, addToStack: Boolean): Boolean {
            val fragmentTransaction = fragmentManager.beginTransaction()
            if(addToStack){
                fragmentTransaction.add(R.id.fragment_container,fragment).addToBackStack(null)
            }else{
                fragmentTransaction.replace(R.id.fragment_container,fragment)
            }

                fragmentTransaction.commit()
            return true
        }
    }

}