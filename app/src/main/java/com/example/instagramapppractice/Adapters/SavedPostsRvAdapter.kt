package com.example.instagramapppractice.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.databinding.SavedPostsRvDesignBinding
import com.squareup.picasso.Picasso

class SavedPostsRvAdapter(val context: Context, val savedList: ArrayList<Posts>) :
    RecyclerView.Adapter<SavedPostsRvAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: SavedPostsRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SavedPostsRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return savedList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Picasso.get().load(savedList[position].postUrl).into(holder.binding.fullImage)
        holder.binding.progressBar2.visibility = View.GONE
    }


}
