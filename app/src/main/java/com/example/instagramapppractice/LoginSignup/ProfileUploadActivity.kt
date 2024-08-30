package com.example.instagramapppractice.LoginSignup

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagramapppractice.HomeActivity
import com.example.instagramapppractice.Modals.User
import com.example.instagramapppractice.Utils.PROFILE_IMAGES
import com.example.instagramapppractice.Utils.USER_NODE
import com.example.instagramapppractice.Utils.uploadImage
import com.example.instagramapppractice.databinding.ActivityProfileUploadBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class ProfileUploadActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityProfileUploadBinding.inflate(layoutInflater)
    }
    private val user = User()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val imageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                if (intent.hasExtra("Update")) {
                    if (intent.getIntExtra("Update", 0) == 1) {
                        uploadImage(uri, PROFILE_IMAGES, this) { url ->
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).update("profileUrl", url)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT)
                                        .show()
                                    binding.profileImage.setImageURI(uri)
                                    binding.finishButton.visibility = View.GONE
                                    binding.skipButton.setText("Continue")
                                    binding.addProfileButton.visibility=View.INVISIBLE
                                }
                        }
                    }
                } else {
                    uploadImage(uri, PROFILE_IMAGES, this) { url ->
                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser!!.uid).update("profileUrl", url)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        binding.profileImage.setImageURI(uri)
                        binding.skipButton.visibility = View.GONE
                        binding.addProfileButton.visibility = View.GONE
                        binding.finishButton.visibility = View.VISIBLE

                    }
                }

            }
        }
        if (intent.hasExtra("Update")) {
            if (intent.getIntExtra("Update", 0) == 1) {
                binding.addProfileButton.setText("Update Profile")
                binding.skipButton.setText("Continue")
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    if (!user?.profileUrl.isNullOrEmpty()){
                        Picasso.get().load(user?.profileUrl).into(binding.profileImage)
                    }else{

                    }

                }
            }
        }
        binding.profileImage.setOnClickListener {
            imageLauncher.launch("image/*")
        }


        binding.addProfileButton.setOnClickListener {
            imageLauncher.launch("image/*")
        }


        binding.finishButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.skipButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }


}
