package com.example.myapplication.collections

import android.net.Uri

data class Collection(
    val collImageUri: Uri= Uri.EMPTY,
    val uid:String = "",
    val collectionName:String = "",
    val type:String = "",
    val creationDate: String = "",
    var itemCount: Int = 0,
    val description: String = "",
    var campos: List<String>? = null,
    var items: List<String>? = null,
    var favorites: Int=0,
    var comments: Int=0,
    var likes: Int=0){
    public fun mapToString(uriString: String):Map<String, Object>{
        val tempCampos = if (this.campos!=null) this.campos else ""
        val tempItems = if (this.items!=null) this.items else ""
        return mapOf(
            "imgUri" to uriString as Object,
            "uid" to this.uid as Object,
            "collName" to this.collectionName as Object,
            "type" to this.type as Object,
            "creationDate" to this.creationDate as Object,
            "itemCount" to this.itemCount as Object,
            "description" to this.description as Object,
            "campos" to tempCampos as Object,
            "items" to tempItems as Object,
            "favorites" to this.favorites as Object,
            "comments" to this.comments as Object,
            "likes" to this.likes as Object
        )
    }
    companion object {
        @JvmStatic
        fun mapToCollection(mapValues: HashMap<String, Any>): Collection {
            lateinit var result: List<String>
            if (mapValues.get("items") is String){
                result = listOf<String>(mapValues.get("items") as String)
            }else{
                result = mapValues.get("items") as List<String>
            }
            return Collection(

                Uri.parse(mapValues.get("imgUri") as String),
                mapValues.get("uid") as String,
                mapValues.get("collName") as String,
                mapValues.get("type") as String,
                mapValues.get("creationDate") as String,
                (mapValues.get("itemCount") as Long).toInt(),
                mapValues.get("description") as String,
                mapValues.get("campos") as List<String>,
                result,
                (mapValues.get("favorites") as Long).toInt(),
                (mapValues.get("comments") as Long).toInt(),
                (mapValues.get("likes") as Long).toInt(),
            )

        }
    }

}
