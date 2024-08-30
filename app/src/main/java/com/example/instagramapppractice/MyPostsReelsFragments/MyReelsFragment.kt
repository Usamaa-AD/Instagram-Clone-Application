package com.example.instagramapppractice.MyPostsReelsFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramapppractice.Adapters.MyReelsAdapter
import com.example.instagramapppractice.Modals.Reels
import com.example.instagramapppractice.Utils.INDIVIDUAL_REELS
import com.example.instagramapppractice.databinding.FragmentMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyReelsFragment : Fragment() {
private lateinit var binding: FragmentMyReelsBinding
private lateinit var adapter: MyReelsAdapter
private lateinit var reelList: ArrayList<Reels>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentMyReelsBinding.inflate(inflater, container, false)
        reelList = ArrayList()
        adapter= MyReelsAdapter(requireContext(),reelList)
        binding.rv.adapter=adapter
        binding.rv.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        Firebase.firestore.collection(INDIVIDUAL_REELS+Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            for (i in it.documents){
                val reels = i.toObject<Reels>()
                reelList.add(reels!!)
            }
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {

    }
}