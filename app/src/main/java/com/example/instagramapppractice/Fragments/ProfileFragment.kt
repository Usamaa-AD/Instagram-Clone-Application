package com.example.instagramapppractice.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramapppractice.Adapters.SearchRvAdapter
import com.example.instagramapppractice.Adapters.ViewPagerAdapter
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.MyPostsReelsFragments.MyPostsFragment
import com.example.instagramapppractice.MyPostsReelsFragments.MyReelsFragment
import com.example.instagramapppractice.PostsUploadActivities.PostActivity
import com.example.instagramapppractice.Utils.FOLLOWED
import com.example.instagramapppractice.Utils.INDIVIDUAL_POST
import com.example.instagramapppractice.Utils.INDIVIDUAL_REELS
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.Utils.showMenu
import com.example.instagramapppractice.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private lateinit var adapter: ViewPagerAdapter
   private lateinit var  binding : FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding =  FragmentProfileBinding.inflate(inflater, container, false)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragments(MyPostsFragment(),"Post")
        adapter.addFragments(MyReelsFragment(),"Reels")
        binding.viewPager.adapter=adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            val user = it.toObject(User::class.java)
            binding.profileFragmentUsername.text = user?.name
            binding.profileFragmentEmail.text = user?.email
            if (!user?.profileUrl.isNullOrEmpty()){
                Picasso.get().load(user?.profileUrl).into(binding.usersProfileImage)
            }
        }
        binding.menuBar.setOnClickListener {
            showMenu(requireContext(),it)
        }
        Firebase.firestore.collection(INDIVIDUAL_POST+Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            binding.postCount.text = it.documents.size.toString()
        }
        Firebase.firestore.collection(INDIVIDUAL_REELS+Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            binding.reelsCount.text = it.documents.size.toString()
        }
        Firebase.firestore.collection(FOLLOWED+Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            binding.followingCount.text = it.documents.size.toString()
        }
        return binding.root
    }


    companion object {

    }
}