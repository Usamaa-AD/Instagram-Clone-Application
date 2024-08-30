package com.example.instagramapppractice.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramapppractice.Adapters.SearchRvAdapter
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.R
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.databinding.ActivityProfileUploadBinding
import com.example.instagramapppractice.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shashank.sony.fancytoastlib.FancyToast

class SearchFragment : Fragment() {
    private lateinit var adapter: SearchRvAdapter
    private lateinit var usersList:ArrayList<User>
    private lateinit var binding: FragmentSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentSearchBinding.inflate(inflater, container, false)
        usersList = ArrayList()
        adapter = SearchRvAdapter(usersList,requireContext())
        binding.rv.adapter = adapter
        binding.rv.layoutManager=LinearLayoutManager(requireContext())
        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            usersList.clear()
            for (i in it.documents){
                if (i.id == Firebase.auth.currentUser!!.uid){

                }else{
                    val user = i.toObject(User::class.java)
                    usersList.add(user!!)
                }

            }
            adapter.notifyDataSetChanged()
        }
        binding.searchButton.setOnClickListener {
            val text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name", text).get()
                .addOnSuccessListener {
                    usersList.clear()
                    if (it.isEmpty){
                        FancyToast.makeText(requireContext(),"Unable to find user",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
                    }else{
                        for (i in it.documents) {
                            if (i.id == Firebase.auth.currentUser!!.uid) {

                            } else {
                                val user = i.toObject(User::class.java)
                                usersList.add(user!!)
                            }

                        }
                    }


                }
        }
        return binding.root
    }

    companion object {

    }
}