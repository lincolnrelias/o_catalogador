package com.example.myapplication.collections
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class CollectionAdapter(private val mList: MutableList<Pair<String?,Collection>>, val activity: MyCollectionsActivity) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {
    var key = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val collKeyPair = mList[position]
        key = collKeyPair.first.toString()
        val collection = collKeyPair.second
        holder.collTitle.text = collection.collectionName
        Picasso.get().load(collection.collImageUri).into(holder.collImage)
        holder.collCreationTime.text = collection.creationDate
        holder.collItemQt.text = collection.itemCount.toString()
        holder.collDesc.text = collection.description
        holder.collLikes.text = collection.likes.toString()
        holder.collComments.text = collection.comments.toString()
        holder.container.setOnClickListener{
            activity.currCollection = collKeyPair
            NavigationUtils.replaceFragment(ViewCollectionFragment(), activity.supportFragmentManager)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val container = itemView.findViewById<ConstraintLayout>(R.id.cl_coll_container)
        val collTitle = itemView.findViewById<AppCompatTextView>(R.id.tv_coll_title)
        val collImage = itemView.findViewById<ImageView>(R.id.iv_coll_img)
        val collCreationTime = itemView.findViewById<TextView>(R.id.tv_timestamp_coll)
        val collItemQt = itemView.findViewById<TextView>(R.id.tv_item_qtd)
        val collDesc = itemView.findViewById<TextView>(R.id.tv_coll_desc)
        val collLikes = itemView.findViewById<TextView>(R.id.likes_counter)
        val collComments = itemView.findViewById<TextView>(R.id.comments_counter)
    }
}
