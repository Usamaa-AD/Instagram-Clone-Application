package com.example.instagramapppractice.Fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramapppractice.PostsUploadActivities.PostActivity
import com.example.instagramapppractice.PostsUploadActivities.ReelsActivity
import com.example.instagramapppractice.databinding.FragmentUploadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        binding.tvAddPost.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()
        }
        binding.tvAddReel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),ReelsActivity::class.java))
            activity?.finish()
        }


        return binding.root
    }

    companion object {

    }



}