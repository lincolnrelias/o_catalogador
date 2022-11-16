package com.example.myapplication

import android.content.Context
import com.example.myapplication.collections.MyCollectionsActivity
import com.example.myapplication.collections.NewCollectionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationUtils {
    companion object {
        @JvmStatic
        fun SetNavigation(navView: BottomNavigationView,context: Context) {
            navView?.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.feed_menu_item->{
                        NavigationUtils.navigateTo(context,FeedActivity())
                    }
                    R.id.collections_menu_item->{
                        NavigationUtils.navigateTo(context, MyCollectionsActivity())
                    }
                    R.id.add_collection_menu_item->{
                        NavigationUtils.navigateTo(context, NewCollectionActivity())
                    }
                    R.id.search_menu_item->{
                        NavigationUtils.navigateTo(context,SearchActivity())
                    }
                    R.id.profile_menu_item->{
                        NavigationUtils.navigateTo(context,ProfileActivity())
                    }
                    //shouldn't get here really
                    else -> {false}
                }
            }
        }
    }
}