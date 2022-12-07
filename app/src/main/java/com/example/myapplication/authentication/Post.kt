package com.example.myapplication.authentication

import android.net.Uri
import java.util.*

data class Post(val authorName:String, val collectionName:String, val imgUri: Uri, val itemName:String?, val timeStamp: Date, val likes: Int,val comments: Int){
}
