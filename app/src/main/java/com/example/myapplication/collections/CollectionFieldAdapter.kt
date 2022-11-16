package com.example.myapplication.collections
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CollectionFieldAdapter(private val mList: MutableList<CollectionField?>) : RecyclerView.Adapter<CollectionFieldAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_field_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val fieldViewModel = mList[position]
        holder.fieldText.text = fieldViewModel?.fieldName
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val fieldText = itemView.findViewById<AppCompatTextView>(R.id.tv_coll_field)
    }
}
