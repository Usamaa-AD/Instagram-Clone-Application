package com.example.instagramapppractice.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramapppractice.Modals.Reels
import com.example.instagramapppractice.databinding.MypostRvDesignBinding
import com.squareup.picasso.Picasso

class MyReelsAdapter(val context: Context,val reelList:ArrayList<Reels>):RecyclerView.Adapter<MyReelsAdapter.MyReelHolder>() {
    inner class MyReelHolder(val binding: MypostRvDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReelHolder {
        val binding = MypostRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyReelHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: MyReelHolder, position: Int) {
        Glide.with(context).load(reelList[position].videoUrl).into(holder.binding.thumbnails)

    }


}