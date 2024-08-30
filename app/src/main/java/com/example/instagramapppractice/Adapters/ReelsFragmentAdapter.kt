package com.example.instagramapppractice.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramapppractice.Modals.Reels
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.databinding.ReelsFragmentRvDesignBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class ReelsFragmentAdapter(val context: Context,val reelList:ArrayList<Reels>):RecyclerView.Adapter<ReelsFragmentAdapter.ReelsHolder>() {
    inner class ReelsHolder(val binding:ReelsFragmentRvDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelsHolder {
        val binding = ReelsFragmentRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ReelsHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ReelsHolder, position: Int) {
        holder.binding.videoView2.setVideoPath(reelList[position].videoUrl)
        holder.binding.videoView2.setOnPreparedListener {
            holder.binding.progressBar.visibility = View.GONE
            holder.binding.videoView2.start()
        }
        holder.binding.videoCaption.text = reelList[position].caption
            Glide.with(context).load(reelList[position].profileUrl).into(holder.binding.imageProfile)
        }
    }

