package com.example.myapplication

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.type.Date
import java.util.*

class NewCollectionActivity : AppCompatActivity() {
    lateinit var navView: BottomNavigationView
    private lateinit var auth: FirebaseAuth
    val ACTIVITY_NUM = 2
    lateinit var collectionObj: Collection
    lateinit var storageRef: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        storageRef = FirebaseStorage.getInstance()
        setContentView(R.layout.activity_new_collection)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView,baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.new_collection)
        findViewById<AppCompatImageView>(R.id.header_config_btn).visibility = View.GONE
        NavigationUtils.replaceFragment(AddCollectionFragment(),supportFragmentManager)
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

    }

}