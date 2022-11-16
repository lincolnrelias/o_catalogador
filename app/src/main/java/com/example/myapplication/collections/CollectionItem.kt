package com.example.myapplication.collections

import android.net.Uri

data class CollectionItem(
    val name: String,
    val collectionId: String,
    val itemImgUri: Uri= Uri.EMPTY,
    val campos: HashMap<String, String>){}
