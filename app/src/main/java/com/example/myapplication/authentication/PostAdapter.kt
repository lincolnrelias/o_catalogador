package com.example.myapplication.authentication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class PostAdapter(private val mList: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val postViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        var text = postViewModel.authorName
        text += if (postViewModel.itemName!=null) " adicionou um novo item a coleção "+
                postViewModel.collectionName+"." else " criou uma nova coleção."
        holder.cardWrapper.visibility = if(postViewModel.itemName!=null) View.GONE else View.VISIBLE
        holder.collectionTextView.text = postViewModel.collectionName
        holder.mainTextView.text = text
        Picasso.get().load(postViewModel.imgUri).into(holder.collImg)
        holder.timestampTextView.text = SimpleDateFormat("hh:mm * dd MMM yyyy",
            Locale("pt","BR")).format(postViewModel.timeStamp)
        holder.likesTextView.text = postViewModel.likes.toString()
        holder.commentsTextView.text = postViewModel.comments.toString()
        holder.likesBtn.setOnClickListener(View.OnClickListener { view ->
            holder.likesBtn.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.heart_full))
        })
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val mainTextView: TextView = itemView.findViewById(R.id.main_text_post)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestamp_post)
        val likesTextView: TextView = itemView.findViewById(R.id.likes_counter)
        val commentsTextView: TextView = itemView.findViewById(R.id.comments_counter)
        val cardWrapper: CardView = itemView.findViewById(R.id.content_card)
        val collectionTextView: TextView = itemView.findViewById(R.id.collection_title)
        val likesCounter: TextView = itemView.findViewById(R.id.likes_counter)
        val collImg: ImageView = itemView.findViewById(R.id.card_image)
        val commentsCounter:TextView = itemView.findViewById(R.id.comments_counter)
        val likesBtn: ImageView = itemView.findViewById(R.id.likes_icon)
        val commentsBtn: ImageView = itemView.findViewById(R.id.comments_icon)
    }
}
