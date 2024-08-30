package com.example.instagramapppractice.PostsUploadActivities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramapppractice.Adapters.SavedPostsRvAdapter
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.R
import com.example.instagramapppractice.Utils.SAVED
import com.example.instagramapppractice.databinding.ActvitySavedPostsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SavedPosts : AppCompatActivity() {
    val binding by lazy {
        ActvitySavedPostsBinding.inflate(layoutInflater)
    }
    private lateinit var savedList:ArrayList<Posts>
    private lateinit var adapter: SavedPostsRvAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        savedList = ArrayList()
        adapter= SavedPostsRvAdapter(this,savedList)
        binding.rv.adapter=adapter
        binding.rv.layoutManager= StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        Firebase.firestore.collection(SAVED + Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            savedList.clear()
            for (i in it.documents){
                val savedPosts =i.toObject(Posts::class.java)
                savedList.add(savedPosts!!)
            }
            adapter.notifyDataSetChanged()
        }
    }
}