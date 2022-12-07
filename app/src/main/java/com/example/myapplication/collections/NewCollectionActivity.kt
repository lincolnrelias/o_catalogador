package com.example.myapplication.collections

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
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
        NavigationUtils.replaceFragment(AddCollectionFragment(), supportFragmentManager,true)
    }

    override fun onStart() {
        showHeader("EditCollection")
        super.onStart()
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
    fun showHeader(header: String){
        findViewById<ConstraintLayout>(R.id.coll_section).visibility = if (header == "Collection") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.main_coll_section).visibility = if (header == "Main") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.coll_new_item_section).visibility = if (header == "NewItem") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.coll_edit_section).visibility = if (header == "EditCollection") View.VISIBLE else View.GONE
        if(header!="Collection" && header!="Main" && header!="NewItem" && header!="EditCollection"){
            findViewById<ConstraintLayout>(R.id.coll_item_section).visibility = View.VISIBLE
            findViewById<ConstraintLayout>(R.id.coll_item_section).visibility = View.VISIBLE
            findViewById<TextView>(R.id.header_title_item_normal).text = header
        }else{
            findViewById<ConstraintLayout>(R.id.coll_item_section).visibility = View.GONE
        }
    }
    fun submitCollection(){
       if(collectionObj.campos!=null){
           storageRef.getReference("images").child(UUID.randomUUID().toString())
               .putFile(collectionObj.collImageUri)
               .addOnCompleteListener{ task->
                   if(task.isSuccessful){
                       Log.d("", "submitCollection: successfull")
                       task.result.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                           val key = databaseRef.getReference("Collections").push().key
                           databaseRef.getReference("Collections").child(key.toString()).setValue(collectionObj.mapToString(it.toString())).addOnCompleteListener {
                               if(it.isSuccessful){
                                   Log.d("", "submitCollection: successfull")
                                   databaseRef.getReference("Posts").push().setValue(listOf(auth.currentUser?.uid,key))
                                   onBackPressed()
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