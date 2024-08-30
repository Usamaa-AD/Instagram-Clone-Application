package com.example.instagramapppractice.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.databinding.MypostRvDesignBinding
import com.squareup.picasso.Picasso

class MyPostsAdapter(val context: android.content.Context,val postList:ArrayList<Posts>):RecyclerView.Adapter<MyPostsAdapter.MyPostHolder>() {
    inner class MyPostHolder(val binding:MypostRvDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostHolder {
        val binding = MypostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyPostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyPostHolder, position: Int) {
        Glide.with(context).load(postList[position].postUrl).into(holder.binding.thumbnails)
    }


}