package com.example.myapplication.authentication

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.FeedActivity
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        findViewById<TextView>(R.id.tv_register).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        if(!hasPermissions()){
            ActivityCompat.requestPermissions(this, getPermissions(),0)
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        findViewById<AppCompatButton>(R.id.btn_login).setOnClickListener{
            val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
            val etsContainer = findViewById<LinearLayoutCompat>(R.id.ets_container)
            progressBar.visibility = View.VISIBLE
            etsContainer.visibility = View.INVISIBLE
            val email = findViewById<EditText>(R.id.et_login_email).text.toString().trim()
            val password = findViewById<EditText>(R.id.et_login_password).text.toString().trim()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    NavigationUtils.navigateTo(baseContext, FeedActivity())
                }else{
                    Toast.makeText(baseContext,"Erro ao logar!",Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    etsContainer.visibility = View.VISIBLE
                }
            }

        }

    }
    fun hasPermissions(): Boolean {
            return ContextCompat.checkSelfPermission(baseContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED &&
                     ContextCompat.checkSelfPermission(baseContext,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED &&
                     ContextCompat.checkSelfPermission(baseContext,android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED
    }
    fun getPermissions(): Array<String>{
        val permissions = mutableListOf<String>()
        permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        permissions.add(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        return permissions.toTypedArray()
    }
}