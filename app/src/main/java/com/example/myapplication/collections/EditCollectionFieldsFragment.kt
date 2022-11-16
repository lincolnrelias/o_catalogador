package com.example.myapplication.collections

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.google.firebase.database.FirebaseDatabase

class EditCollectionFieldsFragment : Fragment() {
    private lateinit var firestore: FirebaseDatabase
    lateinit var galleryImage : ActivityResultLauncher<String>
    lateinit var addCollectionActivity: NewCollectionActivity
    lateinit var imageUri : Uri
    lateinit var collFields : MutableList<CollectionField?>
    override fun onCreate(savedInstanceState: Bundle?) {
        collFields = mutableListOf()
        galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { view?.findViewById<ImageView>(R.id.iv_coll_img)?.setImageURI(it) }
        super.onCreate(savedInstanceState)
        addCollectionActivity = activity as NewCollectionActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_coll_fields, container, false)
    }

    override fun onStart() {
        super.onStart()
        val rvFieldItems = view?.findViewById<RecyclerView>(R.id.rv_add_coll)
        collFields.add(CollectionField("teste1"))
        collFields.add(CollectionField("teste2"))
        val adapter = CollectionFieldAdapter(collFields)
        rvFieldItems?.adapter = adapter
        view?.findViewById<TextView>(R.id.tv_back_add_coll)?.setOnClickListener{
            activity?.onBackPressed()
        }
        view?.findViewById<TextView>(R.id.tv_finish_add_coll)?.setOnClickListener{
            addCollectionActivity.addCollectionFields(collFields)
            addCollectionActivity.submitCollection()
        }
        view?.findViewById<ImageView>(R.id.iv_add_coll_field)?.setOnClickListener{
            collFields.add(CollectionField(view?.findViewById<AppCompatEditText>(R.id.et_add_coll_field)?.text.toString()))
            rvFieldItems?.adapter = CollectionFieldAdapter(collFields)
        }
    }
}