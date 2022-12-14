package com.example.myapplication.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        findViewById<AppCompatImageView>(R.id.banner_register).setOnClickListener{
            onBackPressed()
        }
        findViewById<AppCompatButton>(R.id.btn_register).setOnClickListener {
            val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
            val etsContainer = findViewById<LinearLayoutCompat>(R.id.ets_container)
            progressBar.visibility = View.VISIBLE
            etsContainer.visibility = View.INVISIBLE
            val name = findViewById<EditText>(R.id.et_register_name).text.toString().trim()
            val email = findViewById<EditText>(R.id.et_register_email).text.toString().trim()
            val password = findViewById<EditText>(R.id.et_register_password).text.toString().trim()
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val user = User(name, email, password)
                FirebaseAuth.getInstance().currentUser?.let { it1 ->
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(it1.uid).setValue(user).addOnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(baseContext,"Usu??rio cadastrado com sucesso!",Toast.LENGTH_SHORT).show()
                                onBackPressed()
                            }else{
                                Toast.makeText(baseContext,"Erro ao cadastrar usu??rio!",Toast.LENGTH_SHORT).show()
                                etsContainer.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                            }
                        }
                }
            }.addOnFailureListener {
                Toast.makeText(baseContext,"Erro ao cadastrar usu??rio",Toast.LENGTH_SHORT).show()
                etsContainer.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }
}