package com.example.instagramapppractice.PostsUploadActivities

import android.content.Intent
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.instagramapppractice.HomeActivity
import com.example.instagramapppractice.Modals.Reels
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.Utils.INDIVIDUAL_REELS
import com.example.instagramapppractice.Utils.REEL_FOLDER
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.Utils.uploadVideo
import com.example.instagramapppractice.databinding.ActivityReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shashank.sony.fancytoastlib.FancyToast

class ReelsActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    private val videoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER,this,binding.tvNoVideo,binding.videoView){
                binding.videoView.setVideoURI(uri)
                val mediaController = MediaController(this)
                mediaController.setAnchorView(binding.videoView)
                binding.videoView.setMediaController(mediaController)
                binding.videoView.start()
                videoUrl = it
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.videoView.isVisible = false
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        binding.selectVideo.setOnClickListener {
            videoLauncher.launch("video/*")
        }
        binding.uploadButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                val user = it.toObject(User::class.java)

                val reels = Reels(binding.caption.editText?.text.toString(),videoUrl,user?.profileUrl)
                Firebase.firestore.collection(REEL_FOLDER).document().set(reels).addOnSuccessListener {
                    Firebase.firestore.collection(INDIVIDUAL_REELS+Firebase.auth.currentUser!!.uid).document().set(reels).addOnSuccessListener {
                        FancyToast.makeText(this,"Reel Uploaded Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                        startActivity(Intent(this,HomeActivity::class.java))
                        finish()
                    }
                }
            }

        }


    }
}