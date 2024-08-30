package com.example.instagramapppractice.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.instagramapppractice.Adapters.ReelsFragmentAdapter
import com.example.instagramapppractice.Modals.Reels
import com.example.instagramapppractice.R
import com.example.instagramapppractice.Utils.REEL_FOLDER
import com.example.instagramapppractice.databinding.FragmentReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Suppress("DEPRECATION")
class ReelsFragment : Fragment() {
    private lateinit var adapter: ReelsFragmentAdapter
    private lateinit var reelList: ArrayList<Reels>
    private lateinit var binding: FragmentReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentReelsBinding.inflate(inflater, container, false)
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        reelList = ArrayList()
        adapter = ReelsFragmentAdapter(requireContext(), reelList)
        binding.viewPager.adapter = adapter
        Firebase.firestore.collection(REEL_FOLDER).get().addOnSuccessListener {
            for (i in it.documents) {
                val reels = i.toObject(Reels::class.java)
                reelList.add(reels!!)
            }
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }


    companion object {


    }

}