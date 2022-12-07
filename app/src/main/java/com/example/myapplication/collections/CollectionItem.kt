package com.example.myapplication.collections

import android.net.Uri

data class CollectionItem(
    val name: String,
    val collectionId: String,
    var itemImgUri: Uri= Uri.EMPTY,
    val campos: HashMap<String, String>){
    public fun mapToString(uriString: String):Map<String, Object>{
        return mapOf(
            "name" to name as Object,
            "collectionId" to this.collectionId as Object,
            "itemImgUri" to uriString as Object,
            "campos" to this.campos as Object,
        )
    }
    companion object {
        @JvmStatic
        fun mapToCollectionItem(mapValues: HashMap<String, Any>): CollectionItem {
            return CollectionItem(
                mapValues.get("name") as String,
                mapValues.get("collectionId") as String,
                Uri.parse(mapValues.get("itemImgUri") as String),
                mapValues.get("campos") as HashMap<String, String>,
            )

        }
    }
}
