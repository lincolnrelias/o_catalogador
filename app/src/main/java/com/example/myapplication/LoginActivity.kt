package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
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
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        findViewById<AppCompatButton>(R.id.btn_login).setOnClickListener{
            val email = findViewById<EditText>(R.id.et_login_email).text.toString().trim()
            val password = findViewById<EditText>(R.id.et_login_password).text.toString().trim()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    startActivity(Intent(baseContext,MainActivity::class.java))
                }else{
                    Toast.makeText(baseContext,"Erro ao logar!",Toast.LENGTH_SHORT)
                }
            }

        }

    }
}