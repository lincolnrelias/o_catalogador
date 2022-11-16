package com.example.myapplication.collections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CollectionsFragment : Fragment() {
    private lateinit var databaseRef: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var collections: MutableList<Pair<String?,Collection>>
    override fun onCreate(savedInstanceState: Bundle?) {
        collections = ArrayList()
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("Collections")
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onStart() {
        super.onStart()
        populateRecyclerView()
    }
    fun populateRecyclerView(){
        var currentUid = auth.currentUser?.uid
        if (currentUid!=null){
            databaseRef
                .orderByChild("uid")
                .equalTo(currentUid).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        // Get user object and use the values to update the UI
                        for (collectionData in dataSnapshot.children) {
                            var mapValues = collectionData.value as HashMap<String, Any>
                            collections.add(Pair(collectionData.key,Collection.mapToCollection(mapValues)))
                        }
                        if (!collections?.isEmpty()!!){
                            activity?.findViewById<RecyclerView>(R.id.rv_collections)?.adapter = CollectionAdapter(collections,activity as MyCollectionsActivity)
                        }

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Getting Post failed, log a message
                        Log.w("Error", "loadUser:onCancelled", databaseError.toException())
                    }
                })
        }
    }
}