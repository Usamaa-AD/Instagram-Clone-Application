package com.example.instagramapppractice.MyPostsReelsFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramapppractice.Adapters.MyPostsAdapter
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.Utils.INDIVIDUAL_POST
import com.example.instagramapppractice.Utils.POST_FOLDER
import com.example.instagramapppractice.databinding.FragmentMyPostsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MyPostsFragment : Fragment() {
    private lateinit var binding: FragmentMyPostsBinding
    private lateinit var adapter:MyPostsAdapter
    private lateinit var postList:ArrayList<Posts>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMyPostsBinding.inflate(inflater, container, false)
        postList = ArrayList()
        adapter = MyPostsAdapter(requireContext(),postList)
        binding.rv.adapter=adapter
        binding.rv.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        Firebase.firestore.collection(INDIVIDUAL_POST+Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            for (i in it.documents){
                val post = i.toObject(Posts::class.java)
                postList.add(post!!)
            }
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

    }
}