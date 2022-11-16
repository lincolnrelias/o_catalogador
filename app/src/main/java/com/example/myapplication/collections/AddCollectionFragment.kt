package com.example.myapplication.collections

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddCollectionFragment : Fragment() {
    private lateinit var databaseRef: FirebaseDatabase
    lateinit var storageRef: FirebaseStorage
    lateinit var galleryImage : ActivityResultLauncher<String>
    lateinit var addCollectionActivity: NewCollectionActivity
    lateinit var imageUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        imageUri = Uri.EMPTY
        galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            view?.findViewById<ImageView>(R.id.iv_coll_img)?.setImageURI(it)
            imageUri = it
        }
        super.onCreate(savedInstanceState)
        addCollectionActivity = activity as NewCollectionActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_collection, container, false)
    }

    override fun onStart() {
        super.onStart()
        view?.findViewById<ImageView>(R.id.iv_coll_img)?.setOnClickListener{
            galleryImage.launch("image/*")
        }

        view?.findViewById<AppCompatTextView>(R.id.tv_next_add_coll)?.setOnClickListener{
            addCollectionActivity.createCollection(
                imageUri,
                view?.findViewById<AppCompatEditText>(R.id.et_coll_name)?.text.toString(),
                view?.findViewById<AppCompatEditText>(R.id.et_coll_type)?.text.toString(),
                view?.findViewById<AppCompatEditText>(R.id.tv_date_coll)?.text.toString(),
                view?.findViewById<AppCompatEditText>(R.id.et_coll_desc)?.text.toString(),
            )
            NavigationUtils.replaceFragment(
                EditCollectionFieldsFragment(),
                this.parentFragmentManager
            )
        }
    }
}