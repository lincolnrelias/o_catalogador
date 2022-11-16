package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.authentication.Post
import com.example.myapplication.authentication.PostAdapter
import com.google.firebase.Timestamp
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
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("posts").get()
            .addOnCompleteListener {
                run {

                    var posts: HashMap<String, HashMap<String,Any>> = it.result.documents[0].data as HashMap<String, HashMap<String,Any>>
                    val postList: MutableList<Post> = mutableListOf()
                    posts+=it.result.documents[1].data as HashMap<String, HashMap<String,Any>>
                    for (post in posts.values){
                        post["timeStamp"] = (post["timeStamp"] as Timestamp).toDate()
                        post["likes"] = (post["likes"] as Long).toInt()
                        post["comments"] = (post["comments"] as Long).toInt()
                        postList+=(Post(post))
                    }
                    var recyclerView = view?.findViewById<RecyclerView>(R.id.rv_feed)
                    val adapter = PostAdapter(postList)
                    recyclerView?.adapter = adapter
                }
            }
            .addOnFailureListener { Toast.makeText(context,"Falha", Toast.LENGTH_LONG).show() }
    }

    private fun initializeDatabase() {
        firestore = FirebaseFirestore.getInstance();
        var post: HashMap<String, Post> = HashMap()
        post["id3"] = Post("testuser2","Carrinhoss 5",null, Calendar.getInstance().time, 12, 2)
        post["id4"] = Post("testuser1","Carrinhoss 4","Mclaren verde", Calendar.getInstance().time, 35, 2)
        firestore.collection("posts").add(post)
            .addOnCompleteListener { Toast.makeText(context,"Show", Toast.LENGTH_LONG).show()}
            .addOnFailureListener { Toast.makeText(context,"Falha", Toast.LENGTH_LONG).show() }
    }
}