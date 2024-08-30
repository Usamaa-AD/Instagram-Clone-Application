package com.example.instagramapppractice.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramapppractice.Adapters.HomeRvAdapter
import com.example.instagramapppractice.Adapters.StoriesAdapter
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.R
import com.example.instagramapppractice.Utils.FOLLOWED
import com.example.instagramapppractice.Utils.POST_FOLDER
import com.example.instagramapppractice.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class HomeFragment : Fragment() {
    private lateinit var adapter: HomeRvAdapter
    private lateinit var postsList:ArrayList<Posts>
    private lateinit var storyAdapter: StoriesAdapter
    private lateinit var storyList:ArrayList<User>
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(inflater, container, false)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        postsList=ArrayList()
        adapter=HomeRvAdapter(requireContext(),postsList)
        binding.rv.adapter=adapter
        binding.rv.layoutManager=LinearLayoutManager(requireContext())
        Firebase.firestore.collection(POST_FOLDER).get().addOnSuccessListener {
            for (i in it.documents){
                val post = i.toObject(Posts::class.java)
                postsList.add(post!!)
            }
            adapter.notifyDataSetChanged()
        }
        storyList = ArrayList()
        storyAdapter = StoriesAdapter(requireContext(),storyList)
        binding.rvStories.adapter = storyAdapter
        binding.rvStories.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        Firebase.firestore.collection(FOLLOWED+Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            storyList.clear()
            for (i in it.documents){
                val user = i.toObject(User::class.java)
                storyList.add(user!!)
            }
            storyAdapter.notifyDataSetChanged()
        }



    return binding.root
    }

    companion object {

    }


}