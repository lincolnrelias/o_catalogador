package com.example.myapplication

import android.net.Uri
import java.util.*

data class Collection(
    val collImageUri: Uri,
    val uid:String,
    val collectionName:String,
    val type:String,
    val creationDate: String,
    var itemCount: Int,
    val description: String,
    var campos: List<String>?,
    var items: List<List<Pair<String,String>>>?){}
