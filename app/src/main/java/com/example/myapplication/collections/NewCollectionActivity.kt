package com.example.myapplication.collections

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.myapplication.BottomNavigationUtils
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class NewCollectionActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    private lateinit var auth: FirebaseAuth
    val ACTIVITY_NUM = 2
    lateinit var collectionObj: Collection
    lateinit var storageRef: FirebaseStorage
    lateinit var databaseRef: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        storageRef = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance()
        setContentView(R.layout.activity_new_collection)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView, baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.new_collection)
        findViewById<AppCompatImageView>(R.id.header_config_btn).visibility = View.GONE
        NavigationUtils.replaceFragment(AddCollectionFragment(), supportFragmentManager)
    }

    fun createCollection(imgUri: Uri,collName: String, type :String, creationDate: String, description:String){
        val currentUser = auth.currentUser
        if(currentUser!=null){
            collectionObj = Collection(imgUri, currentUser.uid,collName,type,creationDate,0,description,null,null)
        }

    }

    fun addCollectionFields(collFields: MutableList<CollectionField?>) {
        var strList = mutableListOf<String>()
        collFields.forEach {
            if(it!=null){
                strList.add(it.fieldName)
            }

        }
        collectionObj.campos = Collections.unmodifiableList(strList)
    }

    fun submitCollection(){
       if(collectionObj.campos!=null){
           storageRef.getReference("images")
               .putFile(collectionObj.collImageUri)
               .addOnCompleteListener{ task->
                   if(task.isSuccessful){
                       Log.d("", "submitCollection: successfull")
                       task.result.metadata!!.reference!!.downloadUrl.addOnSuccessListener {

                           databaseRef.getReference("Collections").push().setValue(collectionObj.mapToString(it.toString())).addOnCompleteListener {
                               if(it.isSuccessful){
                                   Log.d("", "submitCollection: successfull")
                               }else{
                                   Log.d("", "submitCollection: failed")
                               }

                           }
                        }
                   }else{
                       Log.d("", "submitCollection: failed")
                   }
               }
       }
    }

}