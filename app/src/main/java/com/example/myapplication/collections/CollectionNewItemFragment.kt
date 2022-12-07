package com.example.myapplication.collections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CollectionNewItemFragment : Fragment() {
    private lateinit var databaseRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    var collection: Pair<String?,Collection>? = null
    lateinit var activity: MyCollectionsActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("Collections")
        activity = getActivity() as MyCollectionsActivity
        collection = activity.currCollection
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection_item_new, container, false)
    }

    override fun onResume() {
        activity.showHeader("NewItem")
        super.onResume()
    }
    override fun onStart() {
        super.onStart()
        view?.findViewById<ImageView>(R.id.iv_coll_item_new)?.setOnClickListener{
            activity.galleryImage.launch("image/*")
        }
        if(collection!=null){
            databaseRef.child(collection!!.first.toString()).child("campos").addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get user object and use the values to update the UI
                        val list:List<String> = dataSnapshot.getValue() as ArrayList<String>
                        view?.findViewById<RecyclerView>(R.id.rv_item_fields)?.adapter = CollectionItemFieldAdapter(list)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Getting Post failed, log a message
                        Log.w("Error", "loadUser:onCancelled", databaseError.toException())
                    }
                }
            )
        }

    }
}