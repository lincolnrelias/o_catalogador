package com.example.myapplication.collections

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.authentication.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso

class ViewCollectionFragment : Fragment() {
     var collection: Collection? = null
    var username:String? = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_collection, container, false)


    }

    override fun onStart() {
        super.onStart()
        val activity = (activity as MyCollectionsActivity)
        collection = activity.currCollection?.second
        activity.showHeader("Collection")
        Picasso.get().load(collection?.collImageUri).into(view?.findViewById(R.id.iv_coll_img))
        view?.findViewById<TextView>(R.id.title)?.text = collection?.collectionName
        view?.findViewById<TextView>(R.id.tv_timestamp)?.text = " *"+collection?.creationDate
        view?.findViewById<TextView>(R.id.tv_item_qtd)?.text = " *"+collection?.itemCount.toString()+" itens"
        view?.findViewById<TextView>(R.id.tv_desc)?.text = collection?.description
        view?.findViewById<TextView>(R.id.fav_counter)?.text = collection?.favorites.toString()
        view?.findViewById<TextView>(R.id.likes_counter)?.text = collection?.likes.toString()
        view?.findViewById<TextView>(R.id.comments_counter)?.text = collection?.comments.toString()
        setUsername()
        var a = mutableListOf(CollectionItem("carro1","",
            Uri.parse("https://firebasestorage.googleapis.com/v0/b/catalogador-d279f.appspot.com/o/images?alt=media&token=01254fe1-47e2-4f4f-92a9-8153583e3fe5"),
            HashMap()))
        a.add(CollectionItem("carro2","", Uri.EMPTY,HashMap()))
        view?.findViewById<RecyclerView>(R.id.rv_coll_itens)?.adapter = CollectionItemAdapter(a,activity as MyCollectionsActivity)
    }
    fun setUsername(){
        val currentUserId = collection?.uid
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        if (currentUserId != null) {
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get user object and use the values to update the UI
                    username = dataSnapshot.getValue<User>()?.name
                    view?.findViewById<TextView>(R.id.tv_username)?.text = "@"+username
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("Error", "loadUser:onCancelled", databaseError.toException())
                }
            }
            reference.child(currentUserId).addListenerForSingleValueEvent(listener)
    }
    }
}