package com.example.myapplication.collections

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BottomNavigationUtils
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap

class MyCollectionsActivity : AppCompatActivity() {
    var currCollection: Pair<String?, Collection>? = null
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 1
    lateinit var storageRef: FirebaseStorage
    lateinit var databaseRef: FirebaseDatabase
    lateinit var galleryImage : ActivityResultLauncher<String>
    var itemImageUri : Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        databaseRef = FirebaseDatabase.getInstance()
        storageRef = FirebaseStorage.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collections)
        navView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        BottomNavigationUtils.SetNavigation(navView, baseContext)
        navView.menu.getItem(ACTIVITY_NUM).isChecked = true
        findViewById<AppCompatTextView>(R.id.header_title)?.text = baseContext.getString(R.string.my_collections)
        showHeader("Main")
        galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            findViewById<ImageView>(R.id.iv_coll_item)?.setImageURI(it)
            itemImageUri = it
            findViewById<ImageView>(R.id.iv_coll_item).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.iv_coll_item_new).visibility = View.GONE
        }
        NavigationUtils.replaceFragment(CollectionsFragment(), supportFragmentManager)
    }

    override fun onStart() {
        findViewById<TextView>(R.id.tv_save_item).setOnClickListener{
            populateitem(itemImageUri)
        }
        super.onStart()
    }
    fun showHeader(header: String){
        findViewById<ConstraintLayout>(R.id.coll_section).visibility = if (header == "Collection") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.main_coll_section).visibility = if (header == "Main") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.coll_new_item_section).visibility = if (header == "NewItem") View.VISIBLE else View.GONE
    }
    fun updateCollectionItems(){

    }
    fun populateitem(uri : Uri = Uri.EMPTY){
        var name = findViewById<EditText>(R.id.et_item_name).text.toString()
        var imgUri = uri
        var rv = findViewById<RecyclerView>(R.id.rv_item_fields)
        var fields = mutableMapOf<String,String>()
        var i = 0
        rv.forEach {
            var key = currCollection?.second?.campos?.get(i)
            var value = ((it as ViewGroup)[0] as EditText).text.toString()
            if (key != null) {
                fields[key] = value
            }
            i++
        }
        currCollection?.second?.let { CollectionItem(name, it.uid,imgUri,fields as HashMap<String, String>) }
            ?.let { submitItem(it) }
    }
    fun submitItem(collectionItem: CollectionItem){
        if(currCollection?.second?.campos!=null){
            storageRef.getReference("images")
                .putFile(collectionItem.itemImgUri)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        task.result.metadata!!.reference!!.downloadUrl.addOnSuccessListener {

                            databaseRef.getReference("Items").push().setValue(collectionItem.mapToString(it.toString())).addOnCompleteListener {
                                if(it.isSuccessful){
                                    updateCollectionItems()
                                    Log.d("", "submitItem: successfull")
                                }else{
                                    Log.d("", "submitItem: failed")
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