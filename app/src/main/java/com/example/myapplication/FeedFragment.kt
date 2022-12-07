package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.authentication.Post
import com.example.myapplication.authentication.PostAdapter
import com.example.myapplication.authentication.User
import com.google.firebase.Timestamp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.example.myapplication.collections.Collection
import com.example.myapplication.collections.CollectionItem
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FeedFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var database: FirebaseDatabase
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFeedItems()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)

    }

    private fun loadFeedItems() {
        firestore = FirebaseFirestore.getInstance()
        database = FirebaseDatabase.getInstance()
        database.getReference("Posts").get().addOnSuccessListener {
            val postList: MutableList<Post> = mutableListOf()
            var i = 0
            var itemCount = it.childrenCount.toInt()
            it.children.forEach { postData->
                var data = (postData.value as ArrayList<String>)
                database.getReference("Users").child(data[0]).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var user = dataSnapshot.getValue<User>()!!
                        database.getReference("Collections").child(data[1]).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                var collection = Collection.mapToCollection(dataSnapshot.value as HashMap<String, Any>)
                                i++
                                if(data.size==3){
                                    database.getReference("Items").child(data[2]).addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            var item = CollectionItem.mapToCollectionItem(dataSnapshot.getValue() as HashMap<String, Any>)
                                            toPost(user, collection, item)?.let { it1 -> postList.add(it1) }
                                            if(i==itemCount){
                                                refreshAdapter(postList)
                                            }
                                        }
                                        override fun onCancelled(databaseError: DatabaseError) {}
                                    })
                                }else{
                                    toPost(user, collection, null)?.let { it1 -> postList.add(it1) }
                                    if(i==itemCount){
                                        refreshAdapter(postList)
                                    }
                                }

                            }

                            override fun onCancelled(databaseError: DatabaseError) {}
                        })

                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            }
        }

//        firestore.collection("posts").get()
//            .addOnCompleteListener {
//                run {
//
//                    var posts: HashMap<String, HashMap<String,Any>> = it.result.documents[0].data as HashMap<String, HashMap<String,Any>>
//                    val postList: MutableList<Post> = mutableListOf()
//                    posts+=it.result.documents[1].data as HashMap<String, HashMap<String,Any>>
//                    for (post in posts.values){
//                        post["timeStamp"] = (post["timeStamp"] as Timestamp).toDate()
//                        post["likes"] = (post["likes"] as Long).toInt()
//                        post["comments"] = (post["comments"] as Long).toInt()
//                        postList+=(Post(post))
//                    }
//                    var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_feed)
//                    val adapter = PostAdapter(postList)
//                    recyclerView?.adapter = adapter
//                }
//            }
//            .addOnFailureListener { Toast.makeText(context,"Falha", Toast.LENGTH_LONG).show() }
    }
    private fun refreshAdapter(postList: List<Post>){
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_feed)
        val adapter = PostAdapter(postList)
        recyclerView?.adapter = adapter
    }
    private fun toPost(user: User, collection: Collection, item: CollectionItem?): Post? {
        return user.name?.let { Post(it,collection.collectionName, collection.collImageUri,
            item?.name,Timestamp.now().toDate(),0,0) }
    }

}