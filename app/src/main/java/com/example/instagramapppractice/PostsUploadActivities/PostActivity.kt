package com.example.instagramapppractice.PostsUploadActivities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramapppractice.HomeActivity
import com.example.instagramapppractice.Modals.Posts
import com.example.instagramapppractice.Utils.INDIVIDUAL_POST
import com.example.instagramapppractice.Utils.POST_FOLDER
import com.example.instagramapppractice.Utils.uploadImage
import com.example.instagramapppractice.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.shashank.sony.fancytoastlib.FancyToast

class PostActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    private lateinit var post: Posts
    private var postUrl: String? = null
    private var postUri:String? =null
    private val imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            postUri = uri.toString()
            uploadImage(uri, POST_FOLDER, this) { url ->
                binding.selectImage.setImageURI(uri)
                postUrl = url
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
        binding.selectImage.setOnClickListener {
            imageLauncher.launch("image/*")
        }
        binding.postButton.setOnClickListener {
            post = Posts(binding.caption.editText?.text.toString(),postUrl,System.currentTimeMillis().toString(),Firebase.auth.currentUser!!.uid)
            Firebase.firestore.collection(POST_FOLDER).document(Firebase.auth.currentUser!!.uid).set(post).addOnSuccessListener {
                Firebase.firestore.collection(INDIVIDUAL_POST+Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                    FancyToast.makeText(this,"Post Uploaded",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show()
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }
        }
        binding.tvSharePost.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, postUri )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(intent,"Share Via"))
        }


    }
}