package com.example.myapplication

import java.util.*

data class Post(val authorName:String, val collectionName:String, val itemName:String?, val timeStamp: Date, val likes: Int,val comments: Int){
    constructor(map: HashMap<String,Any>):this(
        map["authorName"] as String,
        map["collectionName"] as String,
        map["itemName"] as String?,
        map["timeStamp"] as Date,
        map["likes"] as Int,
        map["comments"] as Int
    ){}
}
