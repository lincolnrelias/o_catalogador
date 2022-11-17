package com.example.myapplication.collections

import android.net.Uri

data class CollectionItem(
    val name: String,
    val collectionId: String,
    val itemImgUri: Uri= Uri.EMPTY,
    val campos: HashMap<String, String>){
    public fun mapToString(uriString: String):Map<String, Object>{
        return mapOf(
            "name" to name as Object,
            "collectionId" to this.collectionId as Object,
            "itemImgUri" to uriString as Object,
            "campos" to this.campos as Object,
        )
    }
}
