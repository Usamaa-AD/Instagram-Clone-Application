package com.example.instagramapppractice.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.Utils.FOLLOWED
import com.example.instagramapppractice.databinding.SearchRvDesignBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.callbackFlow

class SearchRvAdapter(val userList:ArrayList<User>,val context: Context):RecyclerView.Adapter<SearchRvAdapter.MyViewHolder>() {
    inner class MyViewHolder(val binding:SearchRvDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SearchRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

      var isFollow = false
        holder.binding.name.text = userList[position].name
        Picasso.get().load(userList[position].profileUrl).into(holder.binding.profile)
        Firebase.firestore.collection(FOLLOWED+Firebase.auth.currentUser!!.uid).whereEqualTo("email",userList[position].email).get().addOnSuccessListener {
            if (it.documents.size==0){
                isFollow=false
            }else{
                holder.binding.followButton.setText("Unfollow")
                isFollow=true
            }
        }


        holder.binding.followButton.setOnClickListener {
            if (isFollow) {
                Firebase.firestore.collection(FOLLOWED + Firebase.auth.currentUser!!.uid)
                    .whereEqualTo("email", userList[position].email).get().addOnSuccessListener {
                    Firebase.firestore.collection(FOLLOWED + Firebase.auth.currentUser!!.uid)
                        .document(it.documents.get(0).id).delete()
                    holder.binding.followButton.setText("Follow")
                        isFollow=false
                }
            }else{
                Firebase.firestore.collection(FOLLOWED+Firebase.auth.currentUser!!.uid).document().set(userList[position])
                holder.binding.followButton.setText("Unfollow")
                isFollow=true
                }

        }

    }

}