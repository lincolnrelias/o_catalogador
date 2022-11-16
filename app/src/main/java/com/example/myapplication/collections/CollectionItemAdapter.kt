package com.example.myapplication.collections
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.NavigationUtils
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class CollectionItemAdapter(private val mList: MutableList<CollectionItem>,val activity: MyCollectionsActivity) : RecyclerView.Adapter<CollectionItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_collections, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val collectionItem = mList[position]
        if (position == mList.size-1){
            holder.container.setOnClickListener {
            NavigationUtils.replaceFragment(CollectionItemFragment(),activity.supportFragmentManager)
            }
        }else{
            Picasso.get().load(collectionItem?.itemImgUri).into(holder.imageView)
            holder.imageView.visibility = View.VISIBLE
            holder.imageViewAdd.visibility = View.GONE
            holder.tvName.text = collectionItem.name
        }




    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_item_name)
        val imageView = itemView.findViewById<ImageView>(R.id.iv_coll_item)
        val imageViewAdd = itemView.findViewById<ImageView>(R.id.iv_coll_item_new)
        val container = itemView.findViewById<LinearLayoutCompat>(R.id.container_coll_item)
    }
}
