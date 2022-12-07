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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID
import kotlin.collections.HashMap

class MyCollectionsActivity : AppCompatActivity() {
    var currCollection: Pair<String?, Collection>? = null
    lateinit var navView: BottomNavigationView
    val ACTIVITY_NUM = 1
    var itemList = mutableListOf<Pair<String?,CollectionItem>>()
    lateinit var storageRef: FirebaseStorage
    lateinit var databaseRef: FirebaseDatabase
    lateinit var galleryImage : ActivityResultLauncher<String>
    var currItemPos = 0
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

        NavigationUtils.replaceFragment(CollectionsFragment(), supportFragmentManager,true)
    }

    override fun onBackPressed() {
        when(supportFragmentManager.backStackEntryCount){
            1->{ return }
            2->{ showHeader("Main") }
            3->{ showHeader("Collection") }
        }
        super.onBackPressed()
    }
    override fun onStart() {
        findViewById<TextView>(R.id.tv_save_item).setOnClickListener{
            populateitem(itemImageUri, null)
        }

        super.onStart()
    }
    fun showHeader(header: String){
        findViewById<ConstraintLayout>(R.id.coll_section).visibility = if (header == "Collection") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.main_coll_section).visibility = if (header == "Main") View.VISIBLE else View.GONE
        findViewById<ConstraintLayout>(R.id.coll_new_item_section).visibility = if (header == "NewItem") View.VISIBLE else View.GONE
        if(header!="Collection" && header!="Main" && header!="NewItem"){
            findViewById<ConstraintLayout>(R.id.coll_item_section).visibility = View.VISIBLE
            findViewById<ConstraintLayout>(R.id.coll_item_section).visibility = View.VISIBLE
            findViewById<TextView>(R.id.header_title_item_normal).text = header
        }else{
            findViewById<ConstraintLayout>(R.id.coll_item_section).visibility = View.GONE
        }
    }
    fun updateCollectionItems(itemId: String?) {
        var newItemList = currCollection?.second?.items?.toMutableList()
        if (newItemList==null){
            newItemList = mutableListOf()
        }
        if (itemId!=null) {
            newItemList.add(itemId)
        }
        currCollection?.second?.let { it ->
            currCollection!!.first?.let { it1 ->
                databaseRef.getReference("Collections").child(it1).child("items").setValue(newItemList).addOnCompleteListener { it1 ->
                    if(it1.isSuccessful){
                        onBackPressed()
                    }
                }
            }
        }
    }
    fun populateitem(uri: Uri = Uri.EMPTY, itemId: String?){
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
        currCollection?.second?.let { currCollection!!.first?.let { it1 ->
            CollectionItem(name,
                it1,imgUri,fields as HashMap<String, String>)
        } }
            ?.let { submitItem(it, itemId) }
    }
    fun submitItem(collectionItem: CollectionItem, itemId: String?){
        if(currCollection?.second?.campos!=null){
            storageRef.getReference("images").child(UUID.randomUUID().toString())
                .putFile(collectionItem.itemImgUri)
                .addOnCompleteListener{ task->
                    if(task.isSuccessful){
                        task.result.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                            if(itemId==null){
                                var id = databaseRef.getReference("Items").push().key
                                if (id != null) {
                                    databaseRef.getReference("Items").child(id).setValue(collectionItem.mapToString(it.toString())).addOnCompleteListener {
                                        if(it.isSuccessful){ updateCollectionItems(id) }
                                    }
                                }
                                databaseRef.getReference("Posts").push().setValue(listOf(FirebaseAuth.getInstance().currentUser?.uid,
                                    currCollection!!.first.toString(),id))
                            }else{
                                databaseRef.getReference("Items").child(itemId).setValue(collectionItem.mapToString(it.toString())).addOnCompleteListener {
                                    if(it.isSuccessful){
                                        getCollectionItems(true)
                                        onBackPressed()
                                    }
                                }
                            }

                        }
                    }else{
                        Log.d("", "submitCollection: failed")
                    }
                }
        }
    }
    fun getCollectionItems(loadAdapter: Boolean){
        databaseRef.getReference("Items").orderByChild("collectionId").equalTo(currCollection?.first).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get user object and use the values to update the UI
                    itemList.clear()

                    for (itemData in dataSnapshot.children) {
                        itemList.add(Pair(itemData.key,CollectionItem.mapToCollectionItem(itemData.value as HashMap<String, Any>)))
                    }
                    if (loadAdapter){
                        itemList.add(Pair(null, CollectionItem("","", Uri.EMPTY,HashMap())))
                        findViewById<RecyclerView>(R.id.rv_coll_itens)?.adapter = CollectionItemAdapter(itemList, navView.context as MyCollectionsActivity)
                        findViewById<RecyclerView>(R.id.rv_coll_itens)?.adapter?.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
}