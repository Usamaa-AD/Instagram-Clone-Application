package com.example.instagramapppractice.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.R
import com.example.instagramapppractice.Utils.SAVED
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.databinding.FragmentHomeDesignBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso


class HomeRvAdapter(val context: Context, private val postsList: ArrayList<Posts>) :
    RecyclerView.Adapter<HomeRvAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding: FragmentHomeDesignBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FragmentHomeDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context).load(postsList[position].postUrl).into(holder.binding.fullImage)
        Firebase.firestore.collection(USER_NODE).document(postsList[position].uid!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                Picasso.get().load(user?.profileUrl).into(holder.binding.profile)
                holder.binding.name.text = user?.name
                val time = TimeAgo.using(postsList[position].time!!.toLong())
                holder.binding.time.text = time
                holder.binding.caption.text = postsList[position].caption
            }
        holder.binding.share.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, postsList[position].postUrl)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(Intent.createChooser(intent, "Share Via"))
        }
        var isSaved = false
        Firebase.firestore.collection(SAVED + Firebase.auth.currentUser!!.uid)
            .whereEqualTo("uid", postsList[position].uid).get().addOnSuccessListener {
                if (it.documents.size == 0) {
                    isSaved = false
                } else {
                    holder.binding.saveButton.setImageResource(R.drawable.saved)
                    isSaved = true
                }
            }

        holder.binding.saveButton.setOnClickListener {
            if (isSaved) {
                Firebase.firestore.collection(SAVED + Firebase.auth.currentUser!!.uid)
                    .whereEqualTo("uid", postsList[position].uid).get().addOnSuccessListener {
                        Firebase.firestore.collection(SAVED + Firebase.auth.currentUser!!.uid)
                            .document(it.documents.get(0).id).delete()
                        holder.binding.saveButton.setImageResource(R.drawable.save_image)
                        Toast.makeText(context, "Unsaved", Toast.LENGTH_SHORT).show()
                        isSaved = false
                    }
            } else {
                Firebase.firestore.collection(SAVED + Firebase.auth.currentUser!!.uid).document()
                    .set(postsList[position])
                holder.binding.saveButton.setImageResource(R.drawable.saved)
                Toast.makeText(context, "Saved ✅", Toast.LENGTH_SHORT).show()
                isSaved = true
            }
        }
        holder.binding.likeButton.setOnClickListener {
            holder.binding.likeButton.setImageResource(R.drawable.red_heart)
        }



    }
}