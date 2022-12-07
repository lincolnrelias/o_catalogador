package com.example.myapplication.colle

import com.example.myapplication.collections.CollectionItemFieldAdapter
import com.example.myapplication.collections.MyCollectionsActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.myapplication.collections.Collection
import com.example.myapplication.collections.CollectionItem
import com.squareup.picasso.Picasso
import java.util.*

class CollectionItemFragment : Fragment() {
    private lateinit var databaseRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var collection: Pair<String?,Collection>? = null
    lateinit var activity: MyCollectionsActivity
    lateinit var itemGalleryImg : ActivityResultLauncher<String>
    lateinit var collectionItem: CollectionItem
    var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("items")
        activity = getActivity() as MyCollectionsActivity
        collection = activity.currCollection
        collectionItem = activity.itemList[activity.currItemPos].second
        itemId = activity.itemList[activity.currItemPos].first
        itemGalleryImg = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            view?.findViewById<ImageView>(R.id.iv_coll_item)?.setImageURI(it)
            collectionItem.itemImgUri = it
        }
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        activity.showHeader(collectionItem.name)

        super.onResume()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_item, container, false)
    }

    override fun onStart() {
        super.onStart()
            initializeViewData(activity.currCollection?.second?.uid == FirebaseAuth.getInstance().uid)
        view?.rootView?.findViewById<TextView>(R.id.tv_save_item_normal)?.setOnClickListener{
            activity.populateitem(collectionItem.itemImgUri,itemId)
        }
    }
    fun initializeViewData(editMode: Boolean){
        if (editMode){
            view?.findViewById<ImageView>(R.id.iv_coll_item)?.setOnClickListener{
                itemGalleryImg.launch("image/*")
            }
            view?.findViewById<TextView>(R.id.tv_edit_collection)?.setOnClickListener{

            }
        }
        var list:MutableList<String> = mutableListOf()
        collectionItem.campos.forEach{
            list.add(it.key+": "+it.value)
        }
        view?.findViewById<EditText>(R.id.et_item_name)?.setText(collectionItem.name)
        Picasso.get().load(collectionItem.itemImgUri).into(view?.findViewById(R.id.iv_coll_item))
        //view?.findViewById<TextView>(R.id.tv_timestamp)?.setText(collectionItem.tim)
        view?.findViewById<RecyclerView>(R.id.rv_item_fields)?.adapter = CollectionItemFieldAdapter(
            ArrayList<String>(list.asReversed()))
        view?.rootView?.findViewById<ImageView>(R.id.arr_back)?.setOnClickListener{
            activity.supportFragmentManager.popBackStack()
        }
    }
}