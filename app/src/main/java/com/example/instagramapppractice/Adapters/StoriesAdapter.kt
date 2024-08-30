package com.example.instagramapppractice.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.databinding.StoryRvDesignBinding
import com.squareup.picasso.Picasso

class StoriesAdapter (val context: Context,val userList:ArrayList<User>):RecyclerView.Adapter<StoriesAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding:StoryRvDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StoryRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Picasso.get().load(userList[position].profileUrl).into(holder.binding.profile)
        holder.binding.name.text = userList[position].name
    }
}