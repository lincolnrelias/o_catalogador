package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class NavigationUtils: AppCompatActivity() {
    companion object {
        @JvmStatic
        fun navigateTo(context : Context, activity: Activity):Boolean {
            var intent = Intent(context,activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return true
        }
    }
}